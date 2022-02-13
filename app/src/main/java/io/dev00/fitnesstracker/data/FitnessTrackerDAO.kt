package io.dev00.fitnesstracker.data

import androidx.room.*
import io.dev00.fitnesstracker.models.Goal
import io.dev00.fitnesstracker.models.Preference
import io.dev00.fitnesstracker.models.Steps
import kotlinx.coroutines.flow.Flow

@Dao
interface FitnessTrackerDAO {
    @Query("SELECT * FROM goals where goal_name=:name")
    fun getGoalByName(name:String):Flow<List<Goal>>

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

    @Query("SELECT * from steps where day=:day AND month=:month AND year=:year LIMIT 1")
    fun getStepsByDate(day:String,month:String,year:String):Flow<List<Steps>>

    @Query("SELECT * from steps where day=:day AND month=:month AND year=:year LIMIT 1")
    fun getOneStepsByDate(day:String,month:String,year:String):Steps?

    @Query("SELECT * from steps where month=:month AND year=:year")
    fun getStepsByMonthAndYear(month:String,year:String):Flow<List<Steps>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSteps(steps: Steps)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSteps(steps: Steps)

    @Delete
    suspend fun deleteSteps(steps: Steps)

    @Query("DELETE FROM steps where month=:month")
    suspend fun deleteAllStepsInAMonth(month:String)

    @Query("SELECT * from preference")
    fun getPreferences(): Flow<List<Preference>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPreference(preference: Preference)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePreference(preference: Preference)

    @Delete
    suspend fun deletePreference(preference: Preference)


}