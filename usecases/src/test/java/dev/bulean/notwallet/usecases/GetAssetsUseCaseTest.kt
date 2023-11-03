package dev.bulean.notwallet.usecases

import dev.bulean.notwallet.data.AssetRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GetAssetsUseCaseTest {

    @Test
    fun `Invoke calls asset repository`(): Unit = runBlocking {
        val assetRepository = mock<AssetRepository>()
        val getAssetsUseCase = GetAssetsUseCase(assetRepository)

        getAssetsUseCase()

        verify(assetRepository).getAssets("GOOGL,AAPL,BTC-USD,ETH-USD,MELI,AMZN,TSLA")
    }
}
