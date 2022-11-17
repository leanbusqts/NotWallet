package dev.bulean.notwallet.model.datasource

import dev.bulean.notwallet.model.database.Quote
import dev.bulean.notwallet.model.database.QuoteDao
import kotlinx.coroutines.flow.Flow

class QuoteLocalDataSource(private val quoteDao: QuoteDao) {

    val quotes: Flow<List<Quote>> = quoteDao.getAll()

    fun findByShortname(shortName: String): Flow<Quote> = quoteDao.findByShortname(shortName)

    suspend fun insert(quote: List<Quote>) {
        quoteDao.insertQuote(quote)
    }
}