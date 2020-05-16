package com.example.android.politicalpreparedness.election

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.network.CivicsApi

//TODO: Create Factory to generate ElectionViewModel with provided election datasource
class ElectionsViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ElectionsViewModel(CivicsApi.retrofitService) as T
    }
}