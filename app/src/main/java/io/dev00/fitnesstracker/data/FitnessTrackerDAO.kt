package io.dev00.fitnesstracker.data

import androidx.room.*
import io.dev00.fitnesstracker.models.Goal
import kotlinx.coroutines.flow.Flow

@Dao
interface FitnessTrackerDAO {
    @Query("SELECT * from goals")
    fun getGoals(): Flow<List<Goal>>

//    @Query("SELECT * from goals where id= :id")
//    suspend fun getNodeByID(id:String):Goal

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(goal: Goal)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(goal:Goal)

    @Query("DELETE  from goals")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteGoal(goal: Goal)
}