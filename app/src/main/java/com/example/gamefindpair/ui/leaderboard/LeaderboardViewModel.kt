package com.example.gamefindpair.ui.leaderboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.gamefindpair.data.repository.LeaderboardRepository
import com.example.gamefindpair.data.model.GameResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LeaderboardViewModel @Inject constructor (
    private val repository: LeaderboardRepository
): ViewModel() {

    val results: LiveData<List<GameResult>>

    init {
        results = repository.allResults
    }
}