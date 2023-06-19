package com.ahmetgur.ahmetgurandroidtask


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ahmetgur.CharacterModels.RelatedTopic
import com.ahmetgur.ahmetgurandroidtask.databinding.ItemSimpsonBinding
import com.squareup.picasso.Picasso

class ListAdapter(isTablet_: Boolean) : RecyclerView.Adapter<ListAdapter.SimpsonViewHolder>() {

    var onItemClick: ((position: Int) -> Unit)? = null
    var isTablet = isTablet_
    private var context: Context? = null


    inner class SimpsonViewHolder(val binding: ItemSimpsonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                val navController = Navigation.findNavController(itemView)
                onItemClick?.invoke(adapterPosition)
                if (!isTablet) {
                    navController.navigate(
                        SimpsonsListFragmentDirections.actionSimpsonsListFragmentToSimpsonsDetailedFragment(
                            getTitle(simpsons, adapterPosition)[0],
                            getTitle(simpsons, adapterPosition)[1],
                            simpsons[adapterPosition].Icon.URL
                        )
                    )
                }
            }
        }
    }

    private val diffCalback = object : DiffUtil.ItemCallback<RelatedTopic>() {
        override fun areItemsTheSame(oldItem: RelatedTopic, newItem: RelatedTopic): Boolean {
            return oldItem.Result == newItem.Result
        }

        override fun areContentsTheSame(oldItem: RelatedTopic, newItem: RelatedTopic): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCalback)
    var simpsons: List<RelatedTopic>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun getItemCount() = simpsons.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpsonViewHolder {
            context = parent.context
        return SimpsonViewHolder(
            ItemSimpsonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )
    }

    override fun onBindViewHolder(holder: SimpsonViewHolder, position: Int) {
        holder.binding.apply {
            val simpson = simpsons[position]
            val title = simpson.Text.split(" -")
            if (isTablet) {
                ivTablet?.setImageResource(R.mipmap.ic_launcher)
                tvTitleTablet?.text = title[0]
                tvDescriptionTablet?.text = simpson.Text
                if (simpson.Icon.URL != "")
                    Picasso.with(context).load("https://duckduckgo.com/".plus(simpson.Icon.URL))
                        .into(ivTablet)
            } else {
                tvTitle?.text = title[0]
            }

        }
    }

    fun getTitle(simpson: List<RelatedTopic>, position: Int) = simpson[position].Text.split(" -")


}