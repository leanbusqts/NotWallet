package dev.bulean.notwallet.framework.server

import arrow.core.Either
import dev.bulean.notwallet.data.APIService
import dev.bulean.notwallet.data.datasource.AssetRemoteDataSource
import dev.bulean.notwallet.domain.Error
import dev.bulean.notwallet.domain.Asset
import dev.bulean.notwallet.framework.tryCall
import javax.inject.Inject

class AssetServerDataSource @Inject constructor(
    private val remoteService: APIService
) : AssetRemoteDataSource {

    override suspend fun getAssets(region: String, lang: String, symbols: String): Either<Error, List<Asset>> = tryCall {
        remoteService.getAssets(region, lang, symbols).quoteResponse.result.toDomainModel()
    }
}

private fun List<RemoteAsset>.toDomainModel(): List<Asset> = map { it.toDomainModel() }

private fun RemoteAsset.toDomainModel(): Asset = Asset(
    currency,
    regularMarketPrice,
    shortName,
    symbol,
    regularMarketChange,
    regularMarketChangePercent,
    regularMarketVolume,
    regularMarketDayRange,
    marketCap,
    fiftyDayAverage,
    twoHundredDayAverage,
    trailingPE,
    trailingAnnualDividendRate,
    trailingAnnualDividendYield,
)
