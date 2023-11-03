package dev.bulean.notwallet.usecases

import dev.bulean.notwallet.domain.Asset
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class PopularAssetsUseCaseTest {

    private val sampleAsset = Asset(
        shortName = "AAPL",
        currency = "USD",
        symbol = "AAPL",
        regularMarketPrice = 0.0,
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

    @Test
    fun `Invoke calls asset repository`(): Unit = runBlocking {
        val assetList = flowOf(listOf(sampleAsset.copy(shortName = "AAPL", symbol = "GOOGL")))
        val popularAssetsUseCase = PopularAssetsUseCase(mock() {
            on { popularAssets } doReturn (assetList)
        })

        val result = popularAssetsUseCase()

        assertEquals(assetList, result)
    }
}
