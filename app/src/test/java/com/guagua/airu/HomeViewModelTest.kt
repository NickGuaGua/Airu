package com.guagua.airu

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.guagua.airu.data.repository.AirRepository
import com.guagua.airu.domain.GetAQIsUseCase
import com.guagua.airu.ui.home.HomeViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
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
class HomeViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel

    @MockK
    private lateinit var repository: AirRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = HomeViewModel(GetAQIsUseCase(repository))
    }

    @Test
    fun `test home page init flow`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        val normalAQI = MockUtil.mockAQI(pm2_5 = 10.5)
        val severeAQI = MockUtil.mockAQI(pm2_5 = 30.0)
        val mockApiResponse = MockUtil.mockAPIResponseWithListingData(listOf(normalAQI, severeAQI))
        coEvery { repository.getAQI(1, 1000) } returns mockApiResponse

        var step = 0
        launch {
            viewModel.getAQIs()
            viewModel.state.collect { state ->
                when (step) {
                    0 -> assert(state.isLoading)
                    1 -> {
                        assertEquals("", state.normalAQIs, listOf(normalAQI))
                        assertEquals("", state.severeAQIs, listOf(severeAQI))
                        assert(!state.isLoading)
                        this.cancel()
                    }
                }
                step++
            }
        }.invokeOnCompletion {
            assert(step == 2)
        }
    }
}