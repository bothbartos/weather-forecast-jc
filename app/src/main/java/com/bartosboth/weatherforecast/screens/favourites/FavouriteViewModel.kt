package com.bartosboth.weatherforecast.screens.favourites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartosboth.weatherforecast.model.Favourite
import com.bartosboth.weatherforecast.repository.FavouriteRepository
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
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavourites().distinctUntilChanged()
                .collect { listOfFavourites ->
                    if (listOfFavourites.isEmpty()) {
                        Log.d("FVM", "FavViewModel: EmptyList")
                        _favouriteList.value = emptyList()
                    } else {
                        _favouriteList.value = listOfFavourites
                        Log.d("FVML", "$listOfFavourites ")
                    }
                }
        }
    }

    fun insertFavourite(favourite: Favourite) =
        viewModelScope.launch { repository.insertFavourite(favourite) }
    fun deleteFavourite(favourite: Favourite) =
        viewModelScope.launch { repository.deleteFavourite(favourite) }
}