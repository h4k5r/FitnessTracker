package io.dev00.fitnesstracker.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.dev00.fitnesstracker.data.FitnessTrackerDAO
import io.dev00.fitnesstracker.data.FitnessTrackerDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun provideFitnessTrackerDao(fitnessTrackerDatabase: FitnessTrackerDatabase): FitnessTrackerDAO {
        return fitnessTrackerDatabase.fitnessTrackerDAO()
    }

    @Singleton
    @Provides
    fun provideFitnessDatabase(@ApplicationContext context: Context): FitnessTrackerDatabase {
        return Room.databaseBuilder(
            context,
            FitnessTrackerDatabase::class.java,
            "fitness_tracker_db"
        )
            .fallbackToDestructiveMigration().build()
    }
}