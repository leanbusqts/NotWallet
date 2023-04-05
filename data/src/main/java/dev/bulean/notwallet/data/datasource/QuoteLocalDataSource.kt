package dev.bulean.notwallet.data.datasource

import dev.bulean.notwallet.domain.Error
import dev.bulean.notwallet.domain.Quote
import kotlinx.coroutines.flow.Flow

interface QuoteLocalDataSource {
    val quotes: Flow<List<Quote>>
    suspend fun isEmpty(): Boolean
    fun findByShortname(shortName: String): Flow<Quote>

    suspend fun insert(quote: List<Quote>): Error?
}
