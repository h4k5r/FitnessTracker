package io.dev00.fitnesstracker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import io.dev00.fitnesstracker.models.Goal
import io.dev00.fitnesstracker.models.Steps

@Database(entities = [Goal::class,Steps::class], version = 1, exportSchema = false)
abstract  class FitnessTrackerDatabase: RoomDatabase() {
    abstract fun fitnessTrackerDAO():FitnessTrackerDAO
}