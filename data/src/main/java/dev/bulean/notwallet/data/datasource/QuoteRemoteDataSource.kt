package dev.bulean.notwallet.data.datasource

import arrow.core.Either
import dev.bulean.notwallet.domain.Quote
import dev.bulean.notwallet.domain.Error

interface QuoteRemoteDataSource {
    suspend fun getQuotes(region: String, lang: String, symbols: String): Either<Error, List<Quote>>
}
