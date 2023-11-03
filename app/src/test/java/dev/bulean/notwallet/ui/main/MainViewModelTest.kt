package dev.bulean.notwallet.ui.main

import dev.bulean.notwallet.CoroutinesTestRule
import dev.bulean.notwallet.domain.Asset
import dev.bulean.notwallet.usecases.GetAssetsUseCase
import dev.bulean.notwallet.usecases.PopularAssetsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var popularAssetsUseCase: PopularAssetsUseCase

    @Mock
    lateinit var getAssetsUseCase: GetAssetsUseCase

    private lateinit var viewModel: MainViewModel

    private val sampleAsset = Asset(
        shortName = "AAPL",
        currency = "USD",
        symbol = "AAPL",
        regularMarketPrice = 0.0,
        regularMarketChange = 0.0,
        regularMarketChangePercent = 0.0,
        regularMarketVolume = 0L,
        regularMarketDayRange = "",
        marketCap = 0L,
        fiftyDayAverage = 0.0,
        twoHundredDayAverage = 0.0,
        trailingPE = 0.0,
        trailingAnnualDividendRate = 0.0,
        trailingAnnualDividendYield = 0.0
    )

    @Before
    fun setUp() {
        whenever(popularAssetsUseCase()).thenReturn(flowOf(listOf(sampleAsset.copy(shortName = "AAPL"))))
        viewModel = MainViewModel(popularAssetsUseCase, getAssetsUseCase)
    }

    @Test
    fun `State is updated with current cached content immediately`() = runTest {
        val results = mutableListOf<MainViewModel.ViewState>()
        val job = launch { viewModel.state.toList(results) }
        runCurrent()
        job.cancel()

        assertEquals(MainViewModel.ViewState(assets = listOf(sampleAsset.copy(shortName = "AAPL"))), results[0])
    }

    @Test
    fun `Progress is shown when screen start and hidden when it finishes requesting assets`() = runTest {
        viewModel.onViewReady()

        val results = mutableListOf<MainViewModel.ViewState>()
        val job = launch { viewModel.state.toList(results) }
        runCurrent()
        job.cancel()

        assertEquals(MainViewModel.ViewState(assets = listOf(sampleAsset.copy(shortName = "AAPL"))), results[0])
    }

    @Test
    fun `Popular assets are requested when screen starts`() = runTest {
        viewModel.onViewReady()

        val results = mutableListOf<MainViewModel.ViewState>()
        val job = launch { viewModel.state.toList(results) }
        runCurrent()
        job.cancel()

        verify(popularAssetsUseCase).invoke()
    }
}
