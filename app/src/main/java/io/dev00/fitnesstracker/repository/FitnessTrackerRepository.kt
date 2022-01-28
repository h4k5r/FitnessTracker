package io.dev00.fitnesstracker.repository

import io.dev00.fitnesstracker.data.FitnessTrackerDAO
import io.dev00.fitnesstracker.models.Goal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class FitnessTrackerRepository @Inject constructor(private val fitnessTrackerDAO: FitnessTrackerDAO) {
    suspend fun addGoal(goal: Goal) {
        fitnessTrackerDAO.insert(goal = goal)
    }

    suspend fun updateGoal(goal: Goal) = fitnessTrackerDAO.update(goal = goal)

    suspend fun deleteGoal(goal: Goal) {
        fitnessTrackerDAO.deleteGoal(goal = goal)
    }

    suspend fun deleteAllGoal() = fitnessTrackerDAO.deleteAll();

    fun getAllGoals(): Flow<List<Goal>> =
        fitnessTrackerDAO.getGoals().flowOn(Dispatchers.IO).conflate()
}