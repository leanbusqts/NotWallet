package dev.bulean.notwallet.ui.detail_asset

import dev.bulean.notwallet.CoroutinesTestRule
import dev.bulean.notwallet.framework.database.Asset
import dev.bulean.notwallet.framework.server.RemoteAsset
import dev.bulean.notwallet.buildDatabaseAssets
import dev.bulean.notwallet.buildRepositoryWith
import dev.bulean.notwallet.usecases.FindAssetByShortnameUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `UI is updated with the movie on start`() = runTest {
        val assetName = "GOOGL"
        val localData = buildDatabaseAssets("GOOGL", "BTC-USD")
        val viewModel = buildViewModelWith(
            assetName = assetName,
            localData = localData
        )
        advanceUntilIdle()
        val result = viewModel.state.value
        assertEquals(localData[0].shortName, result.asset?.shortName)
    }

    private fun buildViewModelWith(
        assetName: String,
        localData: List<Asset> = emptyList(),
        remoteData: List<RemoteAsset> = emptyList()
    ): DetailAssetViewModel {
        val assetsRepository = buildRepositoryWith(localData, remoteData)
        val findAssetByShortnameUseCase = FindAssetByShortnameUseCase(assetsRepository)
        return DetailAssetViewModel(assetName, findAssetByShortnameUseCase)
    }
}
