package com.example.gamefindpair.data.repository

import androidx.lifecycle.LiveData
import com.example.gamefindpair.data.dao.GameResultDao
import com.example.gamefindpair.data.model.GameResult

class LeaderboardRepository(private val dao: GameResultDao) {

    val allResults: LiveData<List<GameResult>> = dao.getAllResults()

    suspend fun insert(result: GameResult) = dao.insert(result)
}