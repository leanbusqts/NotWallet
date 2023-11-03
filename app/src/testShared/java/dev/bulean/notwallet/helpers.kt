package dev.bulean.notwallet

import dev.bulean.notwallet.data.AssetRepository
import dev.bulean.notwallet.data.RegionRepository
import dev.bulean.notwallet.framework.database.AssetRoomDataSource
import dev.bulean.notwallet.framework.server.AssetServerDataSource
import dev.bulean.notwallet.framework.server.RemoteAsset
import dev.bulean.notwallet.framework.database.Asset as DatabaseAsset

fun buildRepositoryWith(
    localData: List<DatabaseAsset>,
    remoteAsset: List<RemoteAsset>
): AssetRepository {
    val locationDataSource = FakeLocationDataSource()
    val permissionChecker = FakePermissionChecker()
    val regionRepository = RegionRepository(locationDataSource, permissionChecker)
    val localDataSource = AssetRoomDataSource(FakeAssetDao(localData))
    val remoteDataSource = AssetServerDataSource(FakeAPIService(remoteAsset))
    return AssetRepository(regionRepository, localDataSource, remoteDataSource)
}

fun buildDatabaseAssets(vararg name: String) = name.map {
    DatabaseAsset(
        currency = "Currency USD",
        regularMarketPrice = 5.0,
        shortName = "$it",
        symbol = "$it",
        regularMarketChange = 0.0,
        regularMarketChangePercent = 0.0,
        regularMarketVolume = 0L,
        regularMarketDayRange = "",
        marketCap = 0L,
        fiftyDayAverage = 0.0,
        twoHundredDayAverage = 0.0,
        trailingPE = 0.0,
        trailingAnnualDividendRate = 0.0,
        trailingAnnualDividendYield = 0.0,
        priceHint = 2,
        preMarketChange = -0.5399933,
        preMarketPrice = 164.75,
        regularMarketPreviousClose = 170.4,
        bid = 164.16,
        ask = 164.65,
        bookValue = 4.402,
        priceToBook = 37.54884,
        averageAnalystRating = "1.8- Buy",
        tradeable = false
    )
}

fun buildRemoteAssets(vararg name: String) = name.map {
    RemoteAsset(
        currency = "Currency USD",
        regularMarketPrice = 5.0,
        shortName = "$it",
        symbol = "$it",
        regularMarketChange = 0.0,
        regularMarketChangePercent = 0.0,
        regularMarketVolume = 0L,
        regularMarketDayRange = "",
        marketCap = 0L,
        fiftyDayAverage = 0.0,
        twoHundredDayAverage = 0.0,
        trailingPE = 0.0,
        trailingAnnualDividendRate = 0.0,
        trailingAnnualDividendYield = 0.0,
        priceHint = 2,
        preMarketChange = -0.5399933,
        preMarketPrice = 164.75,
        regularMarketPreviousClose = 170.4,
        bid = 164.16,
        ask = 164.65,
        bookValue = 4.402,
        priceToBook = 37.54884,
        averageAnalystRating = "1.8- Buy",
        tradeable = false
    )
}
