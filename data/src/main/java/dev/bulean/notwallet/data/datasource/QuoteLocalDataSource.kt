package dev.bulean.notwallet.data.datasource

import dev.bulean.notwallet.domain.Quote
import kotlinx.coroutines.flow.Flow

interface QuoteLocalDataSource {
    val quotes: Flow<List<dev.bulean.notwallet.domain.Quote>>
    fun findByShortname(shortName: String): Flow<dev.bulean.notwallet.domain.Quote>

    suspend fun insert(quote: List<dev.bulean.notwallet.domain.Quote>)
}