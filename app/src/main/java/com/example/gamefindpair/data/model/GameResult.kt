package com.example.gamefindpair.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_results")
data class GameResult(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timeMillis: Long,
    val mode: Int,
    val date: String
)
