package io.dev00.fitnesstracker.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.dev00.fitnesstracker.models.Preference
import io.dev00.fitnesstracker.repository.FitnessTrackerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: FitnessTrackerRepository,
) :
    ViewModel() {
    private var _preferences = MutableStateFlow<List<Preference>>(emptyList())
    var preferences = _preferences.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPreferences().distinctUntilChanged().collect {
                _preferences.value = it
            }
        }
    }

    fun setPreference(preference: Preference) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertPreference(preference = preference)
        }
    }

}