package com.bartosboth.weatherforecast.ui.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartosboth.weatherforecast.data.repository.FavouriteRepository
import com.bartosboth.weatherforecast.data.model.Unit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: FavouriteRepository) : ViewModel() {

    private val _unitSetting = MutableStateFlow(false)
    val unitSetting: StateFlow<Boolean> = _unitSetting.asStateFlow()

    init {
        getUnitSetting()
    }

    fun toggleUnitSetting() {
        viewModelScope.launch {
            val newSetting = !unitSetting.value
            repository.setUnitSetting(newSetting)
            _unitSetting.value = newSetting
        }
    }

    fun getUnitSetting()= viewModelScope.launch {
        repository.getUnitSetting().collect { isImperial ->
            _unitSetting.value = isImperial
        }
    }
}