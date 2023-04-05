package dev.bulean.notwallet.data

import dev.bulean.notwallet.data.datasource.QuoteLocalDataSource
import dev.bulean.notwallet.data.datasource.QuoteRemoteDataSource
import dev.bulean.notwallet.domain.Error
import dev.bulean.notwallet.domain.Quote
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuoteRepository @Inject constructor(
    private val regionRepository: RegionRepository,
    private val localDataSource: QuoteLocalDataSource,
    private val remoteDataSource: QuoteRemoteDataSource
) {

    val popularQuotes = localDataSource.quotes

    fun findByShortname(shortName: String): Flow<Quote> = localDataSource.findByShortname(shortName)

    suspend fun getQuotes(symbols: String): Error? {
        if (localDataSource.isEmpty()) {
            val region: String
            val lang: String
            when(regionRepository.findLastRegion()) {
                "US" -> {
                    region = "US"
                    lang = "en"
                }
                else -> {
                    region = "US"
                    lang = "en"
                }
            }
            val quotes = remoteDataSource.getQuotes(region, lang, symbols)
            quotes.fold(ifLeft = { return it }) {
                localDataSource.insert(it)
            }
        }
        return null
    }
}
