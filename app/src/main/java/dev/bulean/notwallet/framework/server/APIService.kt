package dev.bulean.notwallet.data

import dev.bulean.notwallet.framework.server.RemoteResult
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    // v6/finance/quote?region=US&lang=en&symbols=AAPL%2CBTC-USD
    @GET("v6/finance/quote")
    suspend fun getAssets(
        @Query("region") region: String,
        @Query("lang") lang: String,
        @Query("symbols") symbols: String
    ): RemoteResult
}
