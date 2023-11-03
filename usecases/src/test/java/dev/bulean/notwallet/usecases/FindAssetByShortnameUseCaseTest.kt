package dev.bulean.notwallet.usecases

import dev.bulean.notwallet.domain.Asset
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class FindAssetByShortnameUseCaseTest {

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
        trailingAnnualDividendYield = 0.0
    )

    @Test
    fun `Invoke calls asset repository`(): Unit = runBlocking {
        val asset = flowOf(sampleAsset.copy(shortName = "AAPL"))
        val findAssetUseCase = FindAssetByShortnameUseCase(mock() {
            on { findByShortname("AAPL") } doReturn (asset)
        })

        val result = findAssetUseCase("AAPL")

        assertEquals(asset, result)
    }
}
