package com.example.android.politicalpreparedness.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.politicalpreparedness.databinding.ElectionDetailFragmentBinding


class ElectionDetailFragment : Fragment() {
    val viewModel: ElectionDetailViewModel by viewModels { ElectionDetailViewModelFactory(activity as Context) }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = ElectionDetailFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val election = ElectionDetailFragmentArgs.fromBundle(arguments!!).election

        binding.electionName.text = election.name
        binding.electionDate.text = election.electionDay.toString()
        binding.viewModel = viewModel

        viewModel.election = election

        viewModel.checkElectionFollowStatus()

        binding.info.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://myvote.wi.gov"))
            startActivity(browserIntent)
        }

        return binding.root
    }


}
