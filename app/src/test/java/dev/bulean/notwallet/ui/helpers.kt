package dev.bulean.notwallet.ui

import dev.bulean.notwallet.data.QuoteRepository
import dev.bulean.notwallet.data.RegionRepository
import dev.bulean.notwallet.framework.database.QuoteRoomDataSource
import dev.bulean.notwallet.framework.server.QuoteServerDataSource
import dev.bulean.notwallet.framework.server.RemoteQuote
import dev.bulean.notwallet.framework.database.Quote as DatabaseQuote

fun buildRepositoryWith(
    localData: List<DatabaseQuote>,
    remoteQuote: List<RemoteQuote>
): QuoteRepository {
    val locationDataSource = FakeLocationDataSource()
    val permissionChecker = FakePermissionChecker()
    val regionRepository = RegionRepository(locationDataSource, permissionChecker)
    val localDataSource = QuoteRoomDataSource(FakeQuoteDao(localData))
    val remoteDataSource = QuoteServerDataSource(FakeAPIService(remoteQuote))
    return QuoteRepository(regionRepository, localDataSource, remoteDataSource)
}

fun buildDatabaseQuotes(vararg name: String) = name.map {
    DatabaseQuote(
        currency = "Currency USD",
        regularMarketPrice = 5.0,
        shortName = "$it",
        symbol = "$it"
    )
}

fun buildRemoteQuotes(vararg name: String) = name.map {
    RemoteQuote(
        currency = "Currency USD",
        regularMarketPrice = 5.0,
        shortName = "$it",
        symbol = "$it")
}
