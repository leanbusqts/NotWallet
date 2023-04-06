package dev.bulean.notwallet.data

import arrow.core.right
import dev.bulean.notwallet.data.datasource.QuoteLocalDataSource
import dev.bulean.notwallet.data.datasource.QuoteRemoteDataSource
import dev.bulean.notwallet.domain.Quote
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class QuoteRepositoryTest {

    @Mock
    lateinit var localDataSource: QuoteLocalDataSource

    @Mock
    lateinit var remoteDataSource: QuoteRemoteDataSource

    @Mock
    lateinit var regionRepository: RegionRepository

    lateinit var quoteRepository: QuoteRepository

    private val sampleQuote = Quote(
        shortName = "AAPL",
        currency = "USD",
        symbol = "AAPL",
        regularMarketPrice = 0.0
    )

    private val popularQuotes = flowOf(listOf(sampleQuote.copy(shortName = "AAPL")))

    @Before
    fun setUp() {
        whenever(localDataSource.quotes).thenReturn(popularQuotes)
        quoteRepository = QuoteRepository(regionRepository, localDataSource, remoteDataSource)
    }

    @Test
    fun `Local quotes are taken from local data source ir available`(): Unit = runBlocking {

        val result = quoteRepository.popularQuotes

        assertEquals(popularQuotes, result)
    }

    @Test
    fun `Popular quotes are saved to local data source when it's empty`(): Unit = runBlocking {
        val remoteQuotes = listOf(sampleQuote.copy(shortName = "GOOGL"))
        whenever(localDataSource.isEmpty()).thenReturn(true)
        whenever(regionRepository.findLastRegion()).thenReturn("US")
        whenever(remoteDataSource.getQuotes("US", "en", "GOOGL,AAPL,BTC-USD,ETH-USD,MELI,AMZN,TSLA"))
            .thenReturn(remoteQuotes.right())

        quoteRepository.getQuotes("GOOGL,AAPL,BTC-USD,ETH-USD,MELI,AMZN,TSLA")

        verify(localDataSource).insert(remoteQuotes)
    }

    @Test
    fun `Finding a quote by id is done in local data source`(): Unit = runBlocking {
        val quote = flowOf(sampleQuote.copy(shortName = "MELI"))
        whenever(localDataSource.findByShortname("MELI")).thenReturn(quote)

        val result = quoteRepository.findByShortname("MELI")

        assertEquals(quote, result)
    }
}
