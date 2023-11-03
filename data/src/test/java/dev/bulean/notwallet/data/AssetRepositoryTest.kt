package dev.bulean.notwallet.data

import arrow.core.right
import dev.bulean.notwallet.data.datasource.AssetLocalDataSource
import dev.bulean.notwallet.data.datasource.AssetRemoteDataSource
import dev.bulean.notwallet.domain.Asset
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class AssetRepositoryTest {

    @Mock
    lateinit var localDataSource: AssetLocalDataSource

    @Mock
    lateinit var remoteDataSource: AssetRemoteDataSource

    @Mock
    lateinit var regionRepository: RegionRepository

    lateinit var assetRepository: AssetRepository

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

    private val popularAssets = flowOf(listOf(sampleAsset.copy(shortName = "AAPL")))

    @Before
    fun setUp() {
        whenever(localDataSource.assets).thenReturn(popularAssets)
        assetRepository = AssetRepository(regionRepository, localDataSource, remoteDataSource)
    }

    @Test
    fun `Local assets are taken from local data source ir available`(): Unit = runBlocking {

        val result = assetRepository.popularAssets

        assertEquals(popularAssets, result)
    }

    @Test
    fun `Popular assets are saved to local data source when it's empty`(): Unit = runBlocking {
        val remoteAssets = listOf(sampleAsset.copy(shortName = "GOOGL"))
        whenever(localDataSource.isEmpty()).thenReturn(true)
        whenever(regionRepository.findLastRegion()).thenReturn("US")
        whenever(remoteDataSource.getAssets("US", "en", "GOOGL,AAPL,BTC-USD,ETH-USD,MELI,AMZN,TSLA"))
            .thenReturn(remoteAssets.right())

        assetRepository.getAssets("GOOGL,AAPL,BTC-USD,ETH-USD,MELI,AMZN,TSLA")

        verify(localDataSource).insert(remoteAssets)
    }

    @Test
    fun `Finding a asset by id is done in local data source`(): Unit = runBlocking {
        val asset = flowOf(sampleAsset.copy(shortName = "MELI"))
        whenever(localDataSource.findByShortname("MELI")).thenReturn(asset)

        val result = assetRepository.findByShortname("MELI")

        assertEquals(asset, result)
    }
}
