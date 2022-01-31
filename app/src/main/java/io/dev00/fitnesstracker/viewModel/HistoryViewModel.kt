package io.dev00.fitnesstracker.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.dev00.fitnesstracker.models.Steps
import io.dev00.fitnesstracker.repository.FitnessTrackerRepository
import io.dev00.fitnesstracker.utils.fetChMonthAndYear
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

    private var _selectedMonthSteps = MutableStateFlow<List<Steps>>(emptyList())
    var selectedMonthSteps = _selectedMonthSteps.asStateFlow()
    val TAG = "TAG"
    private var monthYearModel = mutableStateOf("");
    init {
        val date = fetchCurrentDate()
        val month = date.split("/")[1]
        val year = date.split("/")[2]
        monthYearModel.value = fetChMonthAndYear()
        viewModelScope.launch(Dispatchers.IO) {
            repository.getStepsByMonthAndYear(month = month, year = year)
                .distinctUntilChanged()
                .collect {
                        _selectedMonthSteps.value = it
                }
        }
    }
    fun getMonthAndYear():String {
        return monthYearModel.value
    }
    fun setMonthYear(date: String) {
        val month = date.split("/")[0]
        val year = date.split("/")[1]
        viewModelScope.launch(Dispatchers.IO) {
            repository.getStepsByMonthAndYear(month = month, year = year)
                .distinctUntilChanged()
                .collect {
                        Log.d("TAG", "$it")
                        _selectedMonthSteps.value = it
                }
        }
    }


     fun deleteSteps(steps: Steps) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSteps(steps = steps)
        }
    }
}