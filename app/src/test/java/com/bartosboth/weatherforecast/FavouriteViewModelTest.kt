package com.bartosboth.weatherforecast

import com.bartosboth.weatherforecast.data.model.Favourite
import com.bartosboth.weatherforecast.data.repository.FavouriteRepository
import com.bartosboth.weatherforecast.ui.screens.favourites.FavouriteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class FavouriteViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Mock
    private lateinit var repository: FavouriteRepository

    private lateinit var viewModel: FavouriteViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        whenever(repository.getFavourites()).thenReturn(flowOf(emptyList()))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init populates favouriteList when repository returns non-empty list`() = testScope.runTest {
        val favourites = listOf(
            Favourite(city = "London", country = "UK"),
            Favourite(city = "Paris", country = "France")
        )
        whenever(repository.getFavourites()).thenReturn(flowOf(favourites))


        viewModel = FavouriteViewModel(repository)
        advanceUntilIdle()

        assertEquals(favourites, viewModel.favouriteList.value)
    }


    @Test
    fun `init sets empty list when repository returns empty list`() = testScope.runTest {
        viewModel = FavouriteViewModel(repository)
        advanceUntilIdle()

        assertEquals(emptyList<Favourite>(), viewModel.favouriteList.value)
    }

    @Test
    fun `insertFavourite calls repository insertFavourite`() = testScope.runTest {
        viewModel = FavouriteViewModel(repository)
        val favourite = Favourite(city = "Tokyo", country = "Japan")

        viewModel.insertFavourite(favourite)
        advanceUntilIdle()

        verify(repository).insertFavourite(favourite)
    }

    @Test
    fun `deleteFavourite calls repository deleteFavourite`() = testScope.runTest {
        viewModel = FavouriteViewModel(repository)
        val favourite = Favourite(city = "Berlin", country = "Germany")

        viewModel.deleteFavourite(favourite)
        advanceUntilIdle()

        verify(repository).deleteFavourite(favourite)
    }
}
