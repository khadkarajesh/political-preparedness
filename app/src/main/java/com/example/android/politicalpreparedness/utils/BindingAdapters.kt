package com.example.android.politicalpreparedness.utils

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.base.BaseRecyclerViewAdapter

object BindingAdapters {

    /**
     * Use binding adapter to set the recycler view data using livedata object
     */
    @Suppress("UNCHECKED_CAST")
    @BindingAdapter("android:liveData")
    @JvmStatic
    fun <T> setRecyclerViewData(recyclerView: RecyclerView, items: LiveData<List<T>>?) {
        items?.value?.let { itemList ->
            (recyclerView.adapter as? BaseRecyclerViewAdapter<T>)?.apply {
                clear()
                addData(itemList)
            }
        }
    }
}