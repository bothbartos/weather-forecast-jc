package com.bartosboth.weatherforecast

import com.bartosboth.weatherforecast.data.repository.FavouriteRepository
import com.bartosboth.weatherforecast.ui.screens.settings.SettingsViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain


@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelKotestTest: StringSpec({
    lateinit var viewModel: SettingsViewModel
    lateinit var repository: FavouriteRepository
    val testDispatchers = StandardTestDispatcher()

    beforeTest {
        Dispatchers.setMain(testDispatchers)
        repository = mockk()
        viewModel = SettingsViewModel(repository)
    }

    afterTest {
        Dispatchers.resetMain()
    }

    "init should return current unit setting"{
        val initialSetting = false

        testDispatchers.scheduler.advanceUntilIdle()

        viewModel.unitSetting.value shouldBe initialSetting
    }

    "toggleUnitSetting updates state and calls repository"{
        val initialSetting = false

        viewModel.toggleUnitSetting()
        testDispatchers.scheduler.advanceUntilIdle()

        viewModel.unitSetting.value shouldBe !initialSetting
    }

})