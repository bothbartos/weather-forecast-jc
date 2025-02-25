package com.bartosboth.weatherforecast

import com.bartosboth.weatherforecast.data.repository.FavouriteRepository
import com.bartosboth.weatherforecast.ui.screens.settings.SettingsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SettingsViewModelTest {

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
    fun `toggleUnitSetting updates state and calls repository`() = runTest {
        val initialSetting = false

        viewModel.toggleUnitSetting()

        verify(repository).setUnitSetting(!initialSetting)
        assertEquals(!initialSetting, viewModel.unitSetting.value)
    }
}
