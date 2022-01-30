package io.dev00.fitnesstracker.data

import androidx.room.*
import io.dev00.fitnesstracker.models.Goal
import io.dev00.fitnesstracker.models.Steps
import kotlinx.coroutines.flow.Flow

@Dao
interface FitnessTrackerDAO {
    @Query("SELECT * from goals")
    fun getGoals(): Flow<List<Goal>>

    @Query("SELECT * from goals where is_active=1")
    fun getActiveGoal():Flow<List<Goal>>

    @Query("SELECT * from goals where is_active=0")
    fun getInactiveGoals():Flow<List<Goal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: Goal)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateGoal(goal:Goal)

    @Query("DELETE  from goals")
    suspend fun deleteAllGoals()

    @Delete
    suspend fun deleteGoal(goal: Goal)

    @Query("SELECT * from steps where date=:date LIMIT 1")
    fun getStepsByDate(date:String):Flow<List<Steps>>

    @Query("SELECT * from steps where date=:date LIMIT 1")
    fun getOneStepsByDate(date:String):Steps?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSteps(steps: Steps)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSteps(steps: Steps)

    @Delete
    suspend fun deleteSteps(steps: Steps)
}