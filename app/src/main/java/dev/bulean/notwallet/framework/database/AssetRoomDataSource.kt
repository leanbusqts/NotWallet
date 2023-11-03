package dev.bulean.notwallet.framework.database

import dev.bulean.notwallet.data.datasource.AssetLocalDataSource
import dev.bulean.notwallet.domain.Error
import dev.bulean.notwallet.domain.Asset
import dev.bulean.notwallet.framework.tryCall
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import dev.bulean.notwallet.framework.database.Asset as DbAsset

class AssetRoomDataSource @Inject constructor(private val assetDao: AssetDao) : AssetLocalDataSource {

    override val assets: Flow<List<Asset>> = assetDao.getAll().map { it.toDomainModel() }

    override suspend fun isEmpty(): Boolean = assetDao.assetCount() == 0

    override fun findByShortname(shortName: String): Flow<Asset> = assetDao.findByShortname(shortName).map { it.toDomainModel() }

    override suspend fun insert(asset: List<Asset>): Error? = tryCall {
        assetDao.insertAsset(asset.fromDomainModel())
    }.fold(
        ifLeft = { it },
        ifRight = { null }
    )
}

private fun List<DbAsset>.toDomainModel(): List<Asset> = map { it.toDomainModel() }

private fun DbAsset.toDomainModel(): Asset = Asset(
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
    priceHint,
    preMarketChange,
    preMarketPrice,
    regularMarketPreviousClose,
    bid,
    ask,
    bookValue,
    priceToBook,
    averageAnalystRating,
    tradeable,
)

private fun List<Asset>.fromDomainModel(): List<DbAsset> = map { it.fromDomainModel() }

private fun Asset.fromDomainModel(): DbAsset = DbAsset(
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
    priceHint,
    preMarketChange,
    preMarketPrice,
    regularMarketPreviousClose,
    bid,
    ask,
    bookValue,
    priceToBook,
    averageAnalystRating,
    tradeable,
)
