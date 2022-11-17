package dev.bulean.notwallet.model.datasource

import dev.bulean.notwallet.model.YFAPI

class QuoteRemoteDataSource {

    suspend fun getQuotes(region: String, lang: String, symbols: String) =
        YFAPI.retrofitService.getQuotes(region, lang, symbols)
}
