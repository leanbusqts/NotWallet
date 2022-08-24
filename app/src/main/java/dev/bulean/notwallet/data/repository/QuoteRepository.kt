package dev.bulean.notwallet.data.repository

import dev.bulean.notwallet.core.YFAPI
import dev.bulean.notwallet.data.model.QuoteResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuoteRepository {

    suspend fun getQuotes(region: String, lang: String, symbols: String): List<QuoteResult> {
        return withContext(Dispatchers.IO) {
            val response = YFAPI.retrofitService.getQuotes(region, lang, symbols)
            response.body()?.quoteResponse?.result ?: emptyList()
        }
    }
}