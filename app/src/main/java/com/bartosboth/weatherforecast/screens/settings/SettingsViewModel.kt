package com.bartosboth.weatherforecast.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartosboth.weatherforecast.repository.FavouriteRepository
import com.bartosboth.weatherforecast.model.Unit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: FavouriteRepository) : ViewModel() {

    val unitSetting: StateFlow<Boolean> = repository.getUnitSetting()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    fun toggleUnitSetting() {
        viewModelScope.launch {
            val currentSetting = unitSetting.value
            Log.d("UNIT", "toggleUnitSetting: ${unitSetting.value}")
            repository.setUnitSetting(!currentSetting)
        }
    }

    fun setUnitSetting(isImperial: Boolean) {
        viewModelScope.launch {
            repository.setUnitSetting(isImperial)
        }
    }
}