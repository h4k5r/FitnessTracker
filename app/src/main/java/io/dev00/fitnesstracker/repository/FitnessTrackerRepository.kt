package io.dev00.fitnesstracker.repository

import io.dev00.fitnesstracker.data.FitnessTrackerDAO
import io.dev00.fitnesstracker.models.Goal
import io.dev00.fitnesstracker.models.Steps
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class FitnessTrackerRepository @Inject constructor(private val fitnessTrackerDAO: FitnessTrackerDAO) {
    suspend fun insertGoal(goal: Goal) {
        fitnessTrackerDAO.insertGoal(goal = goal)
    }

    suspend fun updateGoal(goal: Goal) = fitnessTrackerDAO.updateGoal(goal = goal)

    suspend fun deleteGoal(goal: Goal) {
        fitnessTrackerDAO.deleteGoal(goal = goal)
    }

    suspend fun deleteAllGoals() = fitnessTrackerDAO.deleteAllGoals()

    fun getAllGoals(): Flow<List<Goal>> =
        fitnessTrackerDAO.getGoals().flowOn(Dispatchers.IO).conflate()

    fun getActiveGoal(): Flow<List<Goal>> =
        fitnessTrackerDAO.getActiveGoal().flowOn(Dispatchers.IO).conflate()

    fun getInactiveGoals(): Flow<List<Goal>> =
        fitnessTrackerDAO.getInactiveGoals().flowOn(Dispatchers.IO).conflate()


    fun getStepByDate(date: String): Flow<List<Steps>> =
        fitnessTrackerDAO.getStepsByDate(date = date)


    suspend fun insertSteps(steps: Steps) = fitnessTrackerDAO.insertSteps(steps = steps)

    suspend fun updateSteps(steps: Steps) = fitnessTrackerDAO.updateSteps(steps = steps)

    suspend fun deleteSteps(steps: Steps) = fitnessTrackerDAO.deleteSteps(steps = steps)
}
