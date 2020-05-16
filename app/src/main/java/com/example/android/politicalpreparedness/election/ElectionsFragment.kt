package com.example.android.politicalpreparedness.election

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.utils.setup

class ElectionsFragment : Fragment() {

    //TODO: Declare ViewModel
    val viewModel: ElectionsViewModel by viewModels { ElectionsViewModelFactory(activity as Context) }
    private lateinit var binding: FragmentElectionBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_election, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
        //TODO: Add ViewModel values and create ViewModel

        //TODO: Add binding values

        //TODO: Link elections to voter info

        //TODO: Initiate recycler adapters

        //TODO: Populate recycler adapters

    }

    //TODO: Refresh adapters when fragment loads

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()
        setupRecyclerviewForSavedElection()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUpcomingElections()
        viewModel.getSavedElections()
    }

    private fun setupRecyclerView() {
        val adapter = ElectionListAdapter {
            this.findNavController().navigate(ElectionsFragmentDirections.actionElectionsFragmentToElectionDetailFragment(it))
        }
        binding.rvUpcomingElections.setup(adapter)
    }

    private fun setupRecyclerviewForSavedElection() {
        val adapter = ElectionListAdapter {
            this.findNavController().navigate(ElectionsFragmentDirections.actionElectionsFragmentToElectionDetailFragment(it))
        }
        binding.rvSavedElections.setup(adapter)
    }
}