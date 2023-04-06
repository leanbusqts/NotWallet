package dev.bulean.notwallet.usecases

import dev.bulean.notwallet.domain.Quote
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class FindQuoteByShortnameUseCaseTest {

    private val sampleQuote = Quote(
        shortName = "AAPL",
        currency = "USD",
        symbol = "AAPL",
        regularMarketPrice = 0.0
    )

    @Test
    fun `Invoke calls quote repository`(): Unit = runBlocking {
        val quote = flowOf(sampleQuote.copy(shortName = "AAPL"))
        val findQuoteUseCase = FindQuoteByShortnameUseCase(mock() {
            on { findByShortname("AAPL") } doReturn (quote)
        })

        val result = findQuoteUseCase("AAPL")

        assertEquals(quote, result)
    }
}
