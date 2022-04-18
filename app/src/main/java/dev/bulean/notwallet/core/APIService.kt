package dev.bulean.notwallet.core

import dev.bulean.notwallet.BuildConfig
import dev.bulean.notwallet.data.model.Quote
import dev.bulean.notwallet.data.model.QuoteResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    // v6/finance/quote?region=US&lang=en&symbols=AAPL%2CBTC-USD
    @GET("v6/finance/quote")
    suspend fun getQuotes(
        @Query("region") region: String,
        @Query("lang") lang: String,
        @Query("symbols") symbols: String
    ) : Response<Quote>

}

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.BASEURL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(getClient())
    .build()

private fun getClient(): OkHttpClient {

    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val headerInterceptor = Interceptor{
        val request = it.request().newBuilder().addHeader(BuildConfig.APIKEY, BuildConfig.APIVALUE)
            .build()
        return@Interceptor it.proceed(request)
    }

    return OkHttpClient.Builder()
        .addInterceptor(headerInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()
}

object YFAPI {
    val retrofitService: APIService by lazy {
        retrofit.create(APIService::class.java)
    }
}