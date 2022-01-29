package io.dev00.fitnesstracker.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.dev00.fitnesstracker.models.Steps
import io.dev00.fitnesstracker.repository.FitnessTrackerRepository
import io.dev00.fitnesstracker.utils.fetchCurrentDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val repository: FitnessTrackerRepository) :
    ViewModel() {
    private var _selectedDateSteps = MutableStateFlow<List<Steps>>(emptyList())
    var selectedDateSteps = _selectedDateSteps.asStateFlow()

    private var dateModel = mutableStateOf("")
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getStepByDate(date = fetchCurrentDate())
                .distinctUntilChanged()
                .collect {
                    if (it.isNullOrEmpty()) {
                        Log.d("TAG", "No Steps data found on the given date")
                        _selectedDateSteps.value = listOf(Steps(steps = 0, date = fetchCurrentDate()))
                    } else {
                        _selectedDateSteps.value = it
                    }
                }
        }
    }
    fun getDate():String {
        return dateModel.value
    }
    fun setDate(date: String) {
        dateModel.value = date
        viewModelScope.launch(Dispatchers.IO) {
            repository.getStepByDate(date = date)
                .distinctUntilChanged()
                .collect {
                    if (it.isNullOrEmpty()) {
                        _selectedDateSteps.value = listOf(Steps(steps = 0, date = date))
                    } else {
                        Log.d("TAG", "$it")
                        _selectedDateSteps.value = it
                    }
                }
        }
    }


     fun deleteSteps(steps: Steps) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSteps(steps = steps)
        }
    }
}