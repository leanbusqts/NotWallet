package dev.bulean.notwallet.ui

import dev.bulean.notwallet.data.APIService
import dev.bulean.notwallet.data.PermissionChecker
import dev.bulean.notwallet.data.datasource.LocationDataSource
import dev.bulean.notwallet.framework.database.QuoteDao
import dev.bulean.notwallet.framework.server.RemoteQuote
import dev.bulean.notwallet.framework.server.RemoteQuoteResponse
import dev.bulean.notwallet.framework.server.RemoteResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import dev.bulean.notwallet.framework.database.Quote as DatabaseQuote

class FakeQuoteDao(quote: List<DatabaseQuote> = emptyList()) : QuoteDao {

    private val inMemoryQuotes = MutableStateFlow(quote)
    private lateinit var findQuoteFlow: MutableStateFlow<DatabaseQuote>

    override fun getAll(): Flow<List<DatabaseQuote>> = inMemoryQuotes

    override suspend fun quoteCount(): Int = inMemoryQuotes.value.size

    override fun findByShortname(shortName: String): Flow<DatabaseQuote> {
        findQuoteFlow = MutableStateFlow(inMemoryQuotes.value.first { it.shortName == shortName })
        return findQuoteFlow
    }

    override suspend fun insertQuote(quotes: List<DatabaseQuote>) {
        inMemoryQuotes.value = quotes
        if (::findQuoteFlow.isInitialized) {
            quotes.firstOrNull { it.shortName == findQuoteFlow.value.shortName }
                ?.let { findQuoteFlow.value = it }
        }
    }
}

class FakeAPIService(private val quotes: List<RemoteQuote> = emptyList(), private val error: Any = "") : APIService {

    override suspend fun getQuotes(region: String, lang: String, symbols: String) = RemoteResult(
        RemoteQuoteResponse(
            error = error,
            result = quotes
        )
    )
}

class FakeLocationDataSource : LocationDataSource {
    var location = "US"

    override suspend fun findLastRegion(): String = location
}

class FakePermissionChecker : PermissionChecker {
    var permissionGranted = true

    override fun check(permission: PermissionChecker.Permission) = permissionGranted
}
