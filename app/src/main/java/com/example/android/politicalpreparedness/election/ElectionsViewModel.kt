package com.example.android.politicalpreparedness.election

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.CivicsApiService
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel(private val api: CivicsApiService, private val dataSource: ElectionDataSource) : ViewModel() {
    val upcomingElections = MutableLiveData<List<Election>>()
    val savedElections = MutableLiveData<List<Election>>()

    fun getUpcomingElections() {
        viewModelScope.launch {
            val electionDeferred = api.getElectionsAsync()
            try {
                var result = electionDeferred.await()
                upcomingElections.value = result.elections
            } catch (e: Exception) {
                Log.e("network error", e.localizedMessage)
            }
        }
    }


    //TODO: Create live data val for upcoming elections

    //TODO: Create live data val for saved elections

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database

    //TODO: Create functions to navigate to saved or upcoming election voter info

}