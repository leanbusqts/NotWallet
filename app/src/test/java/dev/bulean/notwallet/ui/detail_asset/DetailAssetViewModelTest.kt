package dev.bulean.notwallet.ui.detail_asset

import dev.bulean.notwallet.CoroutinesTestRule
import dev.bulean.notwallet.domain.Asset
import dev.bulean.notwallet.usecases.FindAssetByShortnameUseCase
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
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailAssetViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

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

    @Mock
    lateinit var findAssetUseCase: FindAssetByShortnameUseCase

    private lateinit var viewModel: DetailAssetViewModel

    private val asset = sampleAsset.copy(shortName = "GOOGL", symbol = "GOOGL")

    @Before
    fun setup() {
        whenever(findAssetUseCase("GOOGL")).thenReturn(flowOf(asset))
        viewModel = DetailAssetViewModel("GOOGL", findAssetUseCase)
    }

    @Test
    fun `UI is updated with the asset on start`() = runTest {
        val results = mutableListOf<DetailAssetViewModel.ViewState>()
        val job = launch { viewModel.state.toList(results) }
        runCurrent()
        job.cancel()

        assertEquals(DetailAssetViewModel.ViewState(asset = sampleAsset.copy(shortName = "GOOGL", symbol = "GOOGL")), results[0])
    }
}
