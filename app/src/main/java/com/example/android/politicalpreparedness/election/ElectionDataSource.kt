package com.example.android.politicalpreparedness.election

import com.example.android.politicalpreparedness.network.models.Election

interface ElectionDataSource {
    suspend fun getElections(): List<Election>
    suspend fun saveElection(election: Election)
    suspend fun getElection(id: Int): Election?
    suspend fun deleteById(id: Int)
}