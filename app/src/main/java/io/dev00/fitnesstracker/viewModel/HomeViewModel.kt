package io.dev00.fitnesstracker.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.dev00.fitnesstracker.models.Goal
import io.dev00.fitnesstracker.models.Steps
import io.dev00.fitnesstracker.repository.FitnessTrackerRepository
import io.dev00.fitnesstracker.utils.fetchCurrentDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: FitnessTrackerRepository) :
    ViewModel() {

    private var _activeGoal = MutableStateFlow<List<Goal>>(emptyList())
    var activeGoal = _activeGoal.asStateFlow()

    private var _currentSteps = MutableStateFlow<List<Steps>>(emptyList())
    var currentSteps = _currentSteps.asStateFlow()

    private var _selectedDateSteps = MutableStateFlow<List<Steps>>(emptyList())
    var selectedDateSteps = _selectedDateSteps.asStateFlow()


    private var dateModel = mutableStateOf("")

    init {
        val date = fetchCurrentDate()
        val day = date.split("/")[0]
        val month = date.split("/")[1]
        val year = date.split("/")[2]
        dateModel.value = date
        viewModelScope.launch(Dispatchers.IO) {
            repository.getActiveGoal().distinctUntilChanged().collect {
                if (it.isNullOrEmpty()) {
                    Log.d("TAG", "No Active Goal")
                }
                _activeGoal.value = it
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getStepByDate(day = day, month = month, year = year)
                .distinctUntilChanged()
                .collect {
                    if (it.isNullOrEmpty()) {
                        Log.d("TAG", "No Steps data found on the given date")
                        _currentSteps.value = listOf(Steps(steps = 0, day = day, month = month, year = year))
                        _selectedDateSteps.value = listOf(Steps(steps = 0, day = day, month = month, year = year))
                    } else {
                        _currentSteps.value = it
                        _selectedDateSteps.value = it
                    }
                }
        }
    }

    fun getDate(): String {
        return dateModel.value
    }

    fun setDate(date: String) {
        val day = date.split("/")[0]
        val month = date.split("/")[1]
        val year = date.split("/")[2]
        dateModel.value = date
        viewModelScope.launch(Dispatchers.IO) {
            repository.getStepByDate(day = day, month = month, year = year)
                .distinctUntilChanged()
                .collect {
                    if (it.isNullOrEmpty()) {
                        _selectedDateSteps.value = listOf(Steps(steps = 0, day = day, month = month, year = year))
                    } else {
                        Log.d("TAG", "$it")
                        _selectedDateSteps.value = it
                    }
                }
        }
    }


    fun getSingleStepsByDate(date: String,onStepsFetched:(steps:Steps) -> Unit,onStepsNotFound:() -> Unit) {
        val day = date.split("/")[0]
        val month = date.split("/")[1]
        val year = date.split("/")[2]
        viewModelScope.launch(Dispatchers.Main) {
            repository.getStepByDate(day = day, month = month, year = year).collect {
                if (!it.isNullOrEmpty()) {
                    onStepsFetched(it[0])
                } else {
                    onStepsNotFound()
                }
            }
        }
    }

    fun insertSteps(steps: Steps) {
        viewModelScope.launch(Dispatchers.Main) {
            Log.d("TAG", "insertSteps: Executed ")
            repository.insertSteps(steps = steps)
        }
    }

//    fun updateSteps(steps: Steps) {
//        viewModelScope.launch() {
//            Log.d("TAG", "updateSteps: Executed ")
//            repository.updateSteps(steps = steps)
//        }
//    }

}