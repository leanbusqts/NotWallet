package dev.bulean.notwallet.model

import dev.bulean.notwallet.App
import dev.bulean.notwallet.model.database.Quote
import dev.bulean.notwallet.model.datasource.QuoteLocalDataSource
import dev.bulean.notwallet.model.datasource.QuoteRemoteDataSource
import kotlinx.coroutines.flow.Flow

class QuoteRepository(application: App) {

    private val localDataSource = QuoteLocalDataSource(application.database.quoteDao())
    private val remoteDataSource = QuoteRemoteDataSource()

    val popularQuotes = localDataSource.quotes

    fun findByShortname(shortName: String): Flow<Quote> = localDataSource.findByShortname(shortName)

    suspend fun getQuotes(region: String, lang: String, symbols: String) : Error? =
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
