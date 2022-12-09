package dev.bulean.notwallet.data.datasource

import dev.bulean.notwallet.data.RemoteResult

interface QuoteRemoteDataSource {
    suspend fun getQuotes(region: String, lang: String, symbols: String): RemoteResult
}