package com.guagua.airu

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.guagua.airu.data.model.AQI
import com.guagua.airu.data.repository.AirRepository
import com.guagua.airu.ui.search.SearchViewModel
import com.guagua.airu.usecase.GetAQIsUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class SearchViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SearchViewModel

    @MockK
    private lateinit var repository: AirRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = SearchViewModel(GetAQIsUseCase(repository))
    }

    @Test
    fun `test search both site name and county with debouncing`() = runTest {
        // Given
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        val keyword = "special"
        val aqis = mutableListOf<AQI>().apply {
            add(MockUtil.mockAQI(name = "$keyword site"))
            add(MockUtil.mockAQI(county = "$keyword county"))
            addAll(MockUtil.mockAQIs(10))
        }
        val mockApiResponse = MockUtil.mockAPIResponseWithListingData(aqis)
        coEvery { repository.getAQI(1, 1000) } returns mockApiResponse

        // When
        viewModel.search(keyword)

        // Then
        assertEquals(0, viewModel.state.value.searchResult.size)
        delay(800L)
        assertEquals(2, viewModel.state.value.searchResult.size)
    }
}