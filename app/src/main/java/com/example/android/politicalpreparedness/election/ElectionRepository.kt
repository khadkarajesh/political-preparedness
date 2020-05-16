package com.example.android.politicalpreparedness.election

import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class ElectionRepository(private val electionDao: ElectionDao, private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) : ElectionDataSource {
    override suspend fun getElections(): List<Election> = with(ioDispatcher) {
        return electionDao.getElections()
    }

    override suspend fun saveElection(election: Election) = with(ioDispatcher){
        electionDao.insertElection(election)
    }

    override suspend fun getElection(id: Int): Election? = with(ioDispatcher){
        return electionDao.getElectionById(id)
    }

    override suspend fun deleteById(id: Int)= with(ioDispatcher) {
        electionDao.deleteById(id)
    }
}