package io.dev00.fitnesstracker.viewModel

import android.util.Log
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
class EditGoalViewModel @Inject constructor(private val repository: FitnessTrackerRepository) :
    ViewModel() {
    private var _inactiveGoalsList = MutableStateFlow<List<Goal>>(emptyList())
    var inactiveGoals = _inactiveGoalsList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getInactiveGoals().distinctUntilChanged().collect {
                if (it.isNullOrEmpty()) {
                }
                _inactiveGoalsList.value = it
            }
        }
    }

    fun getGoalById(id:Int):Goal {
        return inactiveGoals.value.filter(predicate = {
            it.id == id
        })[0]
    }
    fun insertGoal(goal: Goal,successCallback: () -> Unit = {}, failureCallback: () -> Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            val found = repository.getGoalByName(goal.goalName)
            if (found.isEmpty() || found[0].id == goal.id) {
                repository.insertGoal(goal = goal)
                viewModelScope.launch(Dispatchers.Main) {
                    successCallback()
                }
            } else {
                failureCallback()
            }
        }
    }

}