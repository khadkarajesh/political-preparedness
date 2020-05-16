package com.example.android.politicalpreparedness.detail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.election.ElectionRepository
import com.example.android.politicalpreparedness.network.CivicsApi

//TODO: Create Factory to generate ElectionViewModel with provided election datasource
class ElectionDetailViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ElectionDetailViewModel(ElectionRepository(ElectionDatabase.getInstance(context).electionDao)) as T
    }
}