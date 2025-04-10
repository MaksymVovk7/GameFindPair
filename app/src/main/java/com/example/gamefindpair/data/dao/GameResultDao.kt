package com.example.gamefindpair.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gamefindpair.data.model.GameResult

@Dao
interface GameResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(result: GameResult)

    @Query("SELECT * FROM game_results ORDER BY timeMillis ASC")
    fun getAllResults(): LiveData<List<GameResult>>
}