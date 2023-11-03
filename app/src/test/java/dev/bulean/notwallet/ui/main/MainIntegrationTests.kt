package dev.bulean.notwallet.ui.main

import dev.bulean.notwallet.CoroutinesTestRule
import dev.bulean.notwallet.framework.server.RemoteAsset
import dev.bulean.notwallet.buildDatabaseAssets
import dev.bulean.notwallet.buildRemoteAssets
import dev.bulean.notwallet.buildRepositoryWith
import dev.bulean.notwallet.usecases.GetAssetsUseCase
import dev.bulean.notwallet.usecases.PopularAssetsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import dev.bulean.notwallet.framework.database.Asset as DatabaseAsset

@ExperimentalCoroutinesApi
class MainIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `data is loaded from server when local source is empty`() = runTest {
        val remoteData = buildRemoteAssets("GOOGL", "AAPL", "BTC-USD")
        val viewModel = buildViewModelWith(
            localData = emptyList(),
            remoteData = remoteData
        )
        viewModel.onViewReady()
        advanceUntilIdle()
        val result = viewModel.state.value
        assertEquals(remoteData[0].shortName, result.assets?.get(0)?.shortName)
    }

    @Test
    fun `data is loaded from local source when available`() = runTest {
        val localData = buildDatabaseAssets("GOOGL", "BTC-USD")
        val remoteData = buildRemoteAssets("AAPL", "BTC-USD")
        val viewModel = buildViewModelWith(
            localData = localData,
            remoteData = remoteData
        )
        viewModel.onViewReady()
        advanceUntilIdle()
        val result = viewModel.state.value
        assertEquals(localData[0].shortName, result.assets?.get(0)?.shortName)
    }

    private fun buildViewModelWith(
        localData: List<DatabaseAsset> = emptyList(),
        remoteData: List<RemoteAsset> = emptyList()
    ): MainViewModel {
        val assetsRepository = buildRepositoryWith(localData, remoteData)
        val popularAssetsUseCase = PopularAssetsUseCase(assetsRepository)
        val getAssetsUseCase = GetAssetsUseCase(assetsRepository)
        return MainViewModel(popularAssetsUseCase, getAssetsUseCase)
    }
}
