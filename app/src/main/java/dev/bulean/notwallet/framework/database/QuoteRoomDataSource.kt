package dev.bulean.notwallet.framework.database

import dev.bulean.notwallet.data.datasource.QuoteLocalDataSource
import dev.bulean.notwallet.domain.Error
import dev.bulean.notwallet.domain.Quote
import dev.bulean.notwallet.framework.tryCall
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import dev.bulean.notwallet.framework.database.Quote as DbQuote

class QuoteRoomDataSource @Inject constructor(private val quoteDao: QuoteDao) : QuoteLocalDataSource {

    override val quotes: Flow<List<Quote>> = quoteDao.getAll().map { it.toDomainModel() }

    override fun findByShortname(shortName: String): Flow<Quote> = quoteDao.findByShortname(shortName).map { it.toDomainModel() }

    override suspend fun insert(quote: List<Quote>): Error? = tryCall {
        quoteDao.insertQuote(quote.fromDomainModel())
    }.fold(
        ifLeft = { it },
        ifRight = { null }
    )
}

private fun List<DbQuote>.toDomainModel(): List<Quote> = map { it.toDomainModel() }

private fun DbQuote.toDomainModel(): Quote = Quote(
    currency,
    regularMarketPrice,
    shortName,
    symbol
)

private fun List<Quote>.fromDomainModel(): List<DbQuote> = map { it.fromDomainModel() }

private fun Quote.fromDomainModel(): DbQuote = DbQuote(
    currency,
    regularMarketPrice,
    shortName,
    symbol
)
