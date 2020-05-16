package com.example.android.politicalpreparedness.election.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.base.BaseRecyclerViewAdapter
import com.example.android.politicalpreparedness.databinding.ItemElectionBinding
import com.example.android.politicalpreparedness.network.models.Election


class ElectionListAdapter(callback: (election: Election) -> Unit) : BaseRecyclerViewAdapter<Election>(callback) {
    override fun getLayoutRes(viewType: Int) = R.layout.item_election
}