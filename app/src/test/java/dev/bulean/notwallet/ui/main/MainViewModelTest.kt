package dev.bulean.notwallet.ui.main

import dev.bulean.notwallet.CoroutinesTestRule
import dev.bulean.notwallet.domain.Quote
import dev.bulean.notwallet.usecases.GetQuotesUseCase
import dev.bulean.notwallet.usecases.PopularQuotesUseCase
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
    lateinit var popularQuotesUseCase: PopularQuotesUseCase

    @Mock
    lateinit var getQuotesUseCase: GetQuotesUseCase

    private lateinit var viewModel: MainViewModel

    private val sampleQuote = Quote(
        shortName = "AAPL",
        currency = "USD",
        symbol = "AAPL",
        regularMarketPrice = 0.0
    )

    @Before
    fun setUp() {
        whenever(popularQuotesUseCase()).thenReturn(flowOf(listOf(sampleQuote.copy(shortName = "AAPL"))))
        viewModel = MainViewModel(popularQuotesUseCase, getQuotesUseCase)
    }

    @Test
    fun `State is updated with current cached content immediately`() = runTest {
        val results = mutableListOf<MainViewModel.ViewState>()
        val job = launch { viewModel.state.toList(results) }
        runCurrent()
        job.cancel()

        assertEquals(MainViewModel.ViewState(quotes = listOf(sampleQuote.copy(shortName = "AAPL"))), results[0])
    }

    @Test
    fun `Progress is shown when screen start and hidden when it finishes requesting quotes`() = runTest {
        viewModel.onViewReady()

        val results = mutableListOf<MainViewModel.ViewState>()
        val job = launch { viewModel.state.toList(results) }
        runCurrent()
        job.cancel()

        assertEquals(MainViewModel.ViewState(quotes = listOf(sampleQuote.copy(shortName = "AAPL"))), results[0])
    }

    @Test
    fun `Popular quotes are requested when screen starts`() = runTest {
        viewModel.onViewReady()

        val results = mutableListOf<MainViewModel.ViewState>()
        val job = launch { viewModel.state.toList(results) }
        runCurrent()
        job.cancel()

        verify(popularQuotesUseCase).invoke()
    }
}