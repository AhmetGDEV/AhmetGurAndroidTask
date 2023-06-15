package com.ahmetgur.ahmetgurandroidtask

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmetgur.CharacterModels.RelatedTopic
import com.ahmetgur.ahmetgurandroidtask.databinding.FragmentSimpsonsListBinding
import retrofit2.HttpException
import java.io.IOException

const val TAG = "ListFragment"

class SimpsonsListFragment : Fragment() {

    private lateinit var binding: FragmentSimpsonsListBinding

    private lateinit var listAdapter: ListAdapter

    private var buttonCounter: Int = 0

    private var simpsonsList: List<RelatedTopic> = emptyList()

    private var theWireList: List<RelatedTopic> = emptyList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSimpsonsListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        simpsonsFetchCall()
        theWireFetchCall()

        binding.switchButton.setOnClickListener {
            buttonCounter++
            if (buttonCounter % 2 == 0)
                listAdapter.simpsons = simpsonsList
            else
                listAdapter.simpsons = theWireList
        }
    }

    private fun setupRecyclerView() = binding.rvSimpsons.apply {
        listAdapter = ListAdapter()
        adapter = listAdapter

        layoutManager = LinearLayoutManager(activity)
    }

    private fun simpsonsFetchCall() {
        lifecycleScope.launchWhenCreated {
            binding.progressBar.isVisible = true
            val response = try {
                RetrofitInstance.api.getSimpsons()
            } catch (e: IOException) {
                Log.e(TAG, "IOException")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null){
                simpsonsList = response.body()!!.RelatedTopics
                setupNavigationGraphDirection()
                binding.progressBar.isVisible = false
                listAdapter.simpsons = simpsonsList
            }
        }
    }

    private fun theWireFetchCall() {
        lifecycleScope.launchWhenCreated {
            binding.progressBar.isVisible = true
            val response = try {
                RetrofitInstance.api.getTheWires()
            } catch (e: IOException) {
                Log.e(TAG, "IOException")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null){
                theWireList = response.body()!!.RelatedTopics
                setupNavigationGraphDirection()
                binding.progressBar.isVisible = false
            }
        }
    }

    private fun setupNavigationGraphDirection(){
        listAdapter.onItemClick?.invoke(listAdapter.itemCount)
    }
}