package dev.bulean.notwallet.ui.detail_quote

import dev.bulean.notwallet.CoroutinesTestRule
import dev.bulean.notwallet.domain.Quote
import dev.bulean.notwallet.usecases.FindQuoteByShortnameUseCase
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
class DetailQuoteViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val sampleQuote = Quote(
        shortName = "AAPL",
        currency = "USD",
        symbol = "AAPL",
        regularMarketPrice = 0.0
    )

    @Mock
    lateinit var findQuoteUseCase: FindQuoteByShortnameUseCase

    private lateinit var viewModel: DetailQuoteViewModel

    private val quote = sampleQuote.copy(shortName = "GOOGL", symbol = "GOOGL")

    @Before
    fun setup() {
        whenever(findQuoteUseCase("GOOGL")).thenReturn(flowOf(quote))
        viewModel = DetailQuoteViewModel("GOOGL", findQuoteUseCase)
    }

    @Test
    fun `UI is updated with the quote on start`() = runTest {
        val results = mutableListOf<DetailQuoteViewModel.ViewState>()
        val job = launch { viewModel.state.toList(results) }
        runCurrent()
        job.cancel()

        assertEquals(DetailQuoteViewModel.ViewState(quote = sampleQuote.copy(shortName = "GOOGL", symbol = "GOOGL")), results[0])
    }
}
