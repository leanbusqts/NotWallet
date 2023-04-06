package dev.bulean.notwallet.usecases

import dev.bulean.notwallet.domain.Quote
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class PopularQuotesUseCaseTest {

    private val sampleQuote = Quote(
        shortName = "AAPL",
        currency = "USD",
        symbol = "AAPL",
        regularMarketPrice = 0.0
    )

    @Test
    fun `Invoke calls quote repository`(): Unit = runBlocking {
        val quoteList = flowOf(listOf(sampleQuote.copy(shortName = "AAPL", symbol = "GOOGL")))
        val popularQuotesUseCase = PopularQuotesUseCase(mock() {
            on { popularQuotes } doReturn (quoteList)
        })

        val result = popularQuotesUseCase()

        assertEquals(quoteList, result)
    }
}
