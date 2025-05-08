package com.bartosboth.weatherforecast

import com.bartosboth.weatherforecast.data.model.Favourite
import com.bartosboth.weatherforecast.data.repository.FavouriteRepository
import com.bartosboth.weatherforecast.ui.screens.favourites.FavouriteViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain


@OptIn(ExperimentalCoroutinesApi::class)
class FavouriteViewModelKotestTest: StringSpec({
    lateinit var viewModel: FavouriteViewModel
    lateinit var repository: FavouriteRepository
    val testDispatchers = StandardTestDispatcher()

    beforeTest {
        Dispatchers.setMain(testDispatchers)
        repository = mockk()
        viewModel = FavouriteViewModel(repository)
    }

    afterTest {
        Dispatchers.resetMain()
    }

    "init populates favouriteList when repository returns non-empty list" {
        val favourites = listOf(
            Favourite(city = "London", country = "UK"),
            Favourite(city = "Paris", country = "France")
        )
        coEvery { repository.getFavourites() } returns flowOf(favourites)

        viewModel = FavouriteViewModel(repository)
        testDispatchers.scheduler.advanceUntilIdle()

        viewModel.favouriteList.value shouldBe favourites
    }

    "init sets empty list when repository returns empty list" {
        coEvery { repository.getFavourites() } returns flowOf(emptyList())

        viewModel = FavouriteViewModel(repository)
        testDispatchers.scheduler.advanceUntilIdle()

        viewModel.favouriteList.value shouldBe emptyList<Favourite>()
    }

    "insertFavourite calls repository insertFavourite" {
        coEvery { repository.getFavourites() } returns flowOf(emptyList())
        viewModel = FavouriteViewModel(repository)
        val favourite = Favourite(city = "Tokyo", country = "Japan")

        viewModel.insertFavourite(favourite)
        testDispatchers.scheduler.advanceUntilIdle()

        coVerify { repository.insertFavourite(favourite) }
    }

    "deleteFavourite calls repository deleteFavourite" {
        coEvery { repository.getFavourites() } returns flowOf(emptyList())
        viewModel = FavouriteViewModel(repository)
        val favourite = Favourite(city = "Berlin", country = "Germany")

        viewModel.deleteFavourite(favourite)
        testDispatchers.scheduler.advanceUntilIdle()

        coVerify { repository.deleteFavourite(favourite) }
    }
})