package com.bartosboth.weatherforecast

import com.bartosboth.weatherforecast.data.repository.FavouriteRepository
import com.bartosboth.weatherforecast.ui.screens.settings.SettingsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

    val testDispatcher = StandardTestDispatcher()
    val testScope = TestScope(testDispatcher)

    @Mock
    private lateinit var repository: FavouriteRepository

    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        val unitSettingFlow = MutableStateFlow(false)
        whenever(repository.getUnitSetting()).thenReturn(unitSettingFlow)
        viewModel = SettingsViewModel(repository)
    }

    @Test
    fun `toggleUnitSetting updates state and calls repository`() = testScope.runTest {
        // Arrange
        val initialSetting = false
        whenever(repository.getUnitSetting()).thenReturn(flowOf(initialSetting))
        viewModel = SettingsViewModel(repository) // Re-initialize to pick up the mocked initial setting
        advanceUntilIdle() // Ensure initial setup is complete

        // Act
        viewModel.toggleUnitSetting()
        advanceUntilIdle() // Ensure all coroutines complete

        // Assert
        verify(repository).setUnitSetting(!initialSetting)
        assertEquals(!initialSetting, viewModel.unitSetting.value)
    }

}
