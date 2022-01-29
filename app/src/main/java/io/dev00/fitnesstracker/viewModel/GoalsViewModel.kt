package io.dev00.fitnesstracker.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.dev00.fitnesstracker.models.Goal
import io.dev00.fitnesstracker.repository.FitnessTrackerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GoalsViewModel @Inject constructor(private val repository: FitnessTrackerRepository) :
    ViewModel() {
    private var _activeGoal = MutableStateFlow<List<Goal>>(emptyList())
    private var _inactiveGoalsList = MutableStateFlow<List<Goal>>(emptyList())

    var activeGoal = _activeGoal.asStateFlow()
    var inactiveGoals = _inactiveGoalsList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getActiveGoal().distinctUntilChanged().collect {
                if (it.isNullOrEmpty()) {
                    Log.d("TAG", "No Active Goal")
                }
                _activeGoal.value = it
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getInactiveGoals().distinctUntilChanged().collect {
                if (it.isNullOrEmpty()) {
                    Log.d("TAG", "No Inactive Goals")
                }
                _inactiveGoalsList.value = it
            }
        }

    }

    fun addGoal(goal: Goal) {
        viewModelScope.launch {
            repository.addGoal(goal = goal)
        }
    }

    fun deleteGoal(goal: Goal) {
        viewModelScope.launch {
            repository.deleteGoal(goal = goal)
        }
    }

    fun activateGoal(goal: Goal) {
        viewModelScope.launch {
            goal.isActive = true
            repository.updateGoal(goal = goal)
        }
    }

    fun deactivateGoal(goal: Goal) {
        viewModelScope.launch {
            goal.isActive = false
            repository.updateGoal(goal = goal)
        }
    }
}