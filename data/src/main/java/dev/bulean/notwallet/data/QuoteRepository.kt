package dev.bulean.notwallet.data

import dev.bulean.notwallet.data.datasource.QuoteLocalDataSource
import dev.bulean.notwallet.data.datasource.QuoteRemoteDataSource
import dev.bulean.notwallet.domain.Error
import dev.bulean.notwallet.domain.Quote
import dev.bulean.notwallet.domain.toError
import kotlinx.coroutines.flow.Flow

class QuoteRepository(
    private val localDataSource: QuoteLocalDataSource,
    private val remoteDataSource: QuoteRemoteDataSource
) {

    val popularQuotes = localDataSource.quotes

    fun findByShortname(shortName: String): Flow<Quote> = localDataSource.findByShortname(shortName)

    suspend fun getQuotes(region: String, lang: String, symbols: String): Error? =
        try {
            val quotes = remoteDataSource.getQuotes(region, lang, symbols)
            localDataSource.insert(quotes.quoteResponse.result.toLocalModel())
            null
        } catch (e: Exception) {
            e.toError()
        }
}

private fun List<RemoteQuote>.toLocalModel(): List<Quote> = map { it.toLocalModel() }

private fun RemoteQuote.toLocalModel(): Quote = Quote(
    currency,
    regularMarketPrice,
    shortName,
    symbol
)
