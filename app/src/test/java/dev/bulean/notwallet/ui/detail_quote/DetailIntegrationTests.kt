package dev.bulean.notwallet.ui.detail_quote

import dev.bulean.notwallet.CoroutinesTestRule
import dev.bulean.notwallet.framework.database.Quote
import dev.bulean.notwallet.framework.server.RemoteQuote
import dev.bulean.notwallet.buildDatabaseQuotes
import dev.bulean.notwallet.buildRepositoryWith
import dev.bulean.notwallet.usecases.FindQuoteByShortnameUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `UI is updated with the movie on start`() = runTest {
        val quoteName = "GOOGL"
        val localData = buildDatabaseQuotes("GOOGL", "BTC-USD")
        val viewModel = buildViewModelWith(
            quoteName = quoteName,
            localData = localData
        )
        advanceUntilIdle()
        val result = viewModel.state.value
        assertEquals(localData[0].shortName, result.quote?.shortName)
    }

    private fun buildViewModelWith(
        quoteName: String,
        localData: List<Quote> = emptyList(),
        remoteData: List<RemoteQuote> = emptyList()
    ): DetailQuoteViewModel {
        val quotesRepository = buildRepositoryWith(localData, remoteData)
        val findQuoteByShortnameUseCase = FindQuoteByShortnameUseCase(quotesRepository)
        return DetailQuoteViewModel(quoteName, findQuoteByShortnameUseCase)
    }
}
