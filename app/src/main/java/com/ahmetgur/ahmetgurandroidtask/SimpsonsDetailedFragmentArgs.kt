package com.ahmetgur.ahmetgurandroidtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.ahmetgur.ahmetgurandroidtask.databinding.FragmentSimpsonsDetailedBinding
import com.squareup.picasso.Picasso

class SimpsonsDetailedFragmentArgs : Fragment() {

    private lateinit var binding: FragmentSimpsonsDetailedBinding

    private val args: SimpsonsDetailedFragmentArgsArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSimpsonsDetailedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    fun setUpViews(){
        binding.simpsonImageView.setImageResource(R.mipmap.ic_launcher)
        binding.simpsonTitleText.text = args.title
        binding.simpsonDescriptionText.text = args.textDescription
        if (args.iconURL != "")
            Picasso.with(context).load("https://duckduckgo.com/".plus(args.iconURL)).into(binding.simpsonImageView)
    }
}