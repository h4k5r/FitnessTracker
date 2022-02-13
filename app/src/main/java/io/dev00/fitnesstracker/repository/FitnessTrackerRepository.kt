package io.dev00.fitnesstracker.repository

import io.dev00.fitnesstracker.data.FitnessTrackerDAO
import io.dev00.fitnesstracker.models.Goal
import io.dev00.fitnesstracker.models.Preference
import io.dev00.fitnesstracker.models.Steps
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import java.time.Month
import java.time.Year
import javax.inject.Inject


class FitnessTrackerRepository @Inject constructor(private val fitnessTrackerDAO: FitnessTrackerDAO) {
    fun  getGoalByName(name:String):Flow<List<Goal>> {
        return fitnessTrackerDAO.getGoalByName(name = name).flowOn(Dispatchers.IO).conflate()
    }
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


    fun getStepByDate(day: String, month: String, year: String): Flow<List<Steps>> =
        fitnessTrackerDAO.getStepsByDate(day = day, month = month, year = year)

    fun getStepsByMonthAndYear(month: String, year: String): Flow<List<Steps>> =
        fitnessTrackerDAO.getStepsByMonthAndYear(month = month, year = year)


    suspend fun insertSteps(steps: Steps) = fitnessTrackerDAO.insertSteps(steps = steps)

    suspend fun updateSteps(steps: Steps) = fitnessTrackerDAO.updateSteps(steps = steps)

    suspend fun deleteSteps(steps: Steps) = fitnessTrackerDAO.deleteSteps(steps = steps)

    suspend fun deleteMonth(month: String) = fitnessTrackerDAO.deleteAllStepsInAMonth(month = month)

    suspend fun getPreferences(): Flow<List<Preference>> =
        fitnessTrackerDAO.getPreferences()

    suspend fun insertPreference(preference: Preference) =
        fitnessTrackerDAO.insertPreference(preference = preference)

    suspend fun updatePreference(preference: Preference) =
        fitnessTrackerDAO.updatePreference(preference = preference)

    suspend fun deletePreference(preference: Preference) =
        fitnessTrackerDAO.deletePreference(preference = preference)

}
