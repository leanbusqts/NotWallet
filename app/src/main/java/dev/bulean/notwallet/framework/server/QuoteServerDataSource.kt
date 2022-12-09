package dev.bulean.notwallet.framework.server

import dev.bulean.notwallet.data.YFAPI
import dev.bulean.notwallet.data.datasource.QuoteRemoteDataSource

class QuoteServerDataSource : QuoteRemoteDataSource {

    override suspend fun getQuotes(region: String, lang: String, symbols: String) =
        YFAPI.retrofitService.getQuotes(region, lang, symbols)
}
