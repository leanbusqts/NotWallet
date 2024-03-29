package dev.bulean.notwallet.framework.server

import arrow.core.Either
import dev.bulean.notwallet.data.YFAPI
import dev.bulean.notwallet.data.datasource.QuoteRemoteDataSource
import dev.bulean.notwallet.domain.Error
import dev.bulean.notwallet.domain.Quote
import dev.bulean.notwallet.framework.tryCall
import javax.inject.Inject

class QuoteServerDataSource @Inject constructor() : QuoteRemoteDataSource {

    override suspend fun getQuotes(region: String, lang: String, symbols: String): Either<Error, List<Quote>> = tryCall {
        YFAPI.retrofitService.getQuotes(region, lang, symbols).quoteResponse.result.toDomainModel()
    }
}

private fun List<RemoteQuote>.toDomainModel(): List<Quote> = map { it.toDomainModel() }

private fun RemoteQuote.toDomainModel(): Quote = Quote(
    currency,
    regularMarketPrice,
    shortName,
    symbol
)
