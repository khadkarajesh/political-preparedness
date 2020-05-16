package com.example.android.politicalpreparedness.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.election.ElectionDataSource
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch

class ElectionDetailViewModel(private val dataSource: ElectionDataSource) : ViewModel() {
    var isFollowed: MutableLiveData<Boolean> = MutableLiveData(false)
    lateinit var election: Election


    fun checkElectionFollowStatus() {
        viewModelScope.launch {
            val election = dataSource.getElection(election.id)
            isFollowed.value = election != null
        }
    }

    fun followElection() {
        viewModelScope.launch {
            if (isFollowed.value!!) {
                dataSource.deleteById(election.id)
            } else {
                dataSource.saveElection(election!!)
            }
            isFollowed.value = !isFollowed.value!!
        }
    }

}
