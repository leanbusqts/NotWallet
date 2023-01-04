package dev.bulean.notwallet.data

import dev.bulean.notwallet.data.datasource.QuoteLocalDataSource
import dev.bulean.notwallet.data.datasource.QuoteRemoteDataSource
import dev.bulean.notwallet.domain.Error
import dev.bulean.notwallet.domain.Quote
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuoteRepository @Inject constructor(
    private val localDataSource: QuoteLocalDataSource,
    private val remoteDataSource: QuoteRemoteDataSource
) {

    val popularQuotes = localDataSource.quotes

    fun findByShortname(shortName: String): Flow<Quote> = localDataSource.findByShortname(shortName)

    suspend fun getQuotes(region: String, lang: String, symbols: String): Error? {
        val quotes = remoteDataSource.getQuotes(region, lang, symbols)
        quotes.fold(ifLeft = { return it }) {
            localDataSource.insert(it)
        }
        return null
    }
}
