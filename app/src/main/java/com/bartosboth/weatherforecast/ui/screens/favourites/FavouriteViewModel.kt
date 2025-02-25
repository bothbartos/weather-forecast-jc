package com.bartosboth.weatherforecast.ui.screens.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartosboth.weatherforecast.data.model.Favourite
import com.bartosboth.weatherforecast.data.repository.FavouriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val repository: FavouriteRepository
) : ViewModel() {
    private val _favouriteList = MutableStateFlow<List<Favourite>>(emptyList())
    val favouriteList = _favouriteList.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getFavourites().distinctUntilChanged()
                .collect { listOfFavourites ->
                    _favouriteList.value = listOfFavourites.ifEmpty {
                        emptyList()
                    }
                }
        }
    }

    fun insertFavourite(favourite: Favourite) =
        viewModelScope.launch { repository.insertFavourite(favourite) }
    fun deleteFavourite(favourite: Favourite) =
        viewModelScope.launch { repository.deleteFavourite(favourite) }
}