package dev.bulean.notwallet.ui.main

import dev.bulean.notwallet.CoroutinesTestRule
import dev.bulean.notwallet.framework.server.RemoteQuote
import dev.bulean.notwallet.ui.buildDatabaseQuotes
import dev.bulean.notwallet.ui.buildRemoteQuotes
import dev.bulean.notwallet.ui.buildRepositoryWith
import dev.bulean.notwallet.usecases.GetQuotesUseCase
import dev.bulean.notwallet.usecases.PopularQuotesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import dev.bulean.notwallet.framework.database.Quote as DatabaseQuote

@ExperimentalCoroutinesApi
class MainIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `data is loaded from server when local source is empty`() = runTest {
        val remoteData = buildRemoteQuotes("GOOGL", "AAPL", "BTC-USD")
        val viewModel = buildViewModelWith(
            localData = emptyList(),
            remoteData = remoteData
        )
        viewModel.onViewReady()
        advanceUntilIdle()
        val result = viewModel.state.value
        assertEquals(remoteData[0].shortName, result.quotes?.get(0)?.shortName)
    }

    @Test
    fun `data is loaded from local source when available`() = runTest {
        val localData = buildDatabaseQuotes("GOOGL", "BTC-USD")
        val remoteData = buildRemoteQuotes("AAPL", "BTC-USD")
        val viewModel = buildViewModelWith(
            localData = localData,
            remoteData = remoteData
        )
        viewModel.onViewReady()
        advanceUntilIdle()
        val result = viewModel.state.value
        assertEquals(localData[0].shortName, result.quotes?.get(0)?.shortName)
    }

    private fun buildViewModelWith(
        localData: List<DatabaseQuote> = emptyList(),
        remoteData: List<RemoteQuote> = emptyList()
    ): MainViewModel {
        val quotesRepository = buildRepositoryWith(localData, remoteData)
        val popularQuotesUseCase = PopularQuotesUseCase(quotesRepository)
        val getQuotesUseCase = GetQuotesUseCase(quotesRepository)
        return MainViewModel(popularQuotesUseCase, getQuotesUseCase)
    }
}
