package dev.bulean.notwallet.usecases

import dev.bulean.notwallet.data.QuoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GetQuotesUseCaseTest {

    @Test
    fun `Invoke calls quote repository`(): Unit = runBlocking {
        val quoteRepository = mock<QuoteRepository>()
        val getQuotesUseCase = GetQuotesUseCase(quoteRepository)

        getQuotesUseCase()

        verify(quoteRepository).getQuotes("GOOGL,AAPL,BTC-USD,ETH-USD,MELI,AMZN,TSLA")
    }
}
