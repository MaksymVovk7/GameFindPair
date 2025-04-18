package com.example.gamefindpair.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gamefindpair.data.dao.GameResultDao
import com.example.gamefindpair.data.model.GameResult

@Database(entities = [GameResult::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameResultDao(): GameResultDao

//    companion object {
//        @Volatile private var INSTANCE: AppDatabase? = null
//
//        fun getDatabase(context: Context): AppDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    "game_database"
//                ).build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
}