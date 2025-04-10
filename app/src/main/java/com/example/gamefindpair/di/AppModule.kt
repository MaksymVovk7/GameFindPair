package com.example.gamefindpair.di

import android.content.Context
import androidx.room.Room
import com.example.gamefindpair.data.repository.LeaderboardRepository
import com.example.gamefindpair.data.AppDatabase
import com.example.gamefindpair.data.dao.GameResultDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "game_database"
        ).build()
    }

    @Provides
    fun provideGameResultDao(db: AppDatabase): GameResultDao {
        return db.gameResultDao()
    }

    @Provides
    @Singleton
    fun provideLeaderboardRepository(dao: GameResultDao): LeaderboardRepository {
        return LeaderboardRepository(dao)
    }
}