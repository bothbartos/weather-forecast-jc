package com.bartosboth.weatherforecast

//import com.bartosboth.weatherforecast.data.WeatherDao
//import com.bartosboth.weatherforecast.data.model.Favourite
//import com.bartosboth.weatherforecast.data.model.Unit
//import com.bartosboth.weatherforecast.data.repository.FavouriteRepository
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.runBlocking
//import kotlinx.coroutines.test.runTest
//import org.junit.Assert.assertEquals
//import org.junit.Before
//import org.junit.Test
//import org.mockito.Mock
//import org.mockito.MockitoAnnotations
//import org.mockito.kotlin.verify
//import org.mockito.kotlin.whenever
//
//class FavouriteRepositoryTest {
//
//    @Mock
//    private lateinit var weatherDao: WeatherDao
//
//    private lateinit var repository: FavouriteRepository
//
//    @Before
//    fun setup() {
//        MockitoAnnotations.openMocks(this)
//        repository = FavouriteRepository(weatherDao)
//    }
//
//    @Test
//    fun `getFavourites returns flow from weatherDao`() = runTest {
//        val favourites = listOf(Favourite("London", "UK"), Favourite("Paris", "France"))
//        whenever(weatherDao.getFavourites()).thenReturn(flowOf(favourites))
//
//        val result = repository.getFavourites().first()
//
//        assertEquals(favourites, result)
//    }
//
//    @Test
//    fun `insertFavourite calls weatherDao insertFavourite`() = runTest {
//        val favourite = Favourite("Tokyo", "Japan")
//
//        repository.insertFavourite(favourite)
//
//        verify(weatherDao).insertFavourite(favourite)
//    }
//
//    @Test
//    fun `deleteFavourite calls weatherDao deleteFavourite`() = runTest {
//        val favourite = Favourite("Berlin", "Germany")
//
//        repository.deleteFavourite(favourite)
//
//        verify(weatherDao).deleteFavourite(favourite)
//    }
//
//    @Test
//    fun `getUnitSetting returns flow of boolean from weatherDao`() = runTest {
//        val unitSetting = Unit(isImperial = true)
//        whenever(weatherDao.getUnitSetting()).thenReturn(flowOf(unitSetting))
//
//        val result = repository.getUnitSetting().first()
//
//        assertEquals(true, result)
//    }
//
//    @Test
//    fun `getUnitSetting returns false when weatherDao returns null`() = runTest {
//        whenever(weatherDao.getUnitSetting()).thenReturn(flowOf(null))
//
//        val result = repository.getUnitSetting().first()
//
//        assertEquals(false, result)
//    }
//
//    @Test
//    fun `setUnitSetting calls weatherDao insertUnitSetting`() = runTest {
//        val isImperial = true
//
//        repository.setUnitSetting(isImperial)
//
//        verify(weatherDao).insertUnitSetting(Unit(isImperial = isImperial))
//    }
//}
