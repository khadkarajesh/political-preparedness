package com.example.android.politicalpreparedness.utils


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.base.BaseRecyclerViewAdapter


/**
 * Extension function to setup the RecyclerView
 */
fun <T> RecyclerView.setup(
        adapter: BaseRecyclerViewAdapter<T>
) {
    this.apply {
        layoutManager = LinearLayoutManager(this.context)
        this.adapter = adapter
    }
}
