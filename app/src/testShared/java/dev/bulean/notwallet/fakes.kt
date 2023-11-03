package dev.bulean.notwallet

import dev.bulean.notwallet.data.APIService
import dev.bulean.notwallet.data.PermissionChecker
import dev.bulean.notwallet.data.datasource.LocationDataSource
import dev.bulean.notwallet.framework.database.AssetDao
import dev.bulean.notwallet.framework.server.RemoteAsset
import dev.bulean.notwallet.framework.server.RemoteAssetResponse
import dev.bulean.notwallet.framework.server.RemoteResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import dev.bulean.notwallet.framework.database.Asset as DatabaseAsset

class FakeAssetDao(asset: List<DatabaseAsset> = emptyList()) : AssetDao {

    private val inMemoryAssets = MutableStateFlow(asset)
    private lateinit var findAssetFlow: MutableStateFlow<DatabaseAsset>

    override fun getAll(): Flow<List<DatabaseAsset>> = inMemoryAssets

    override suspend fun assetCount(): Int = inMemoryAssets.value.size

    override fun findByShortname(shortName: String): Flow<DatabaseAsset> {
        findAssetFlow = MutableStateFlow(inMemoryAssets.value.first { it.shortName == shortName })
        return findAssetFlow
    }

    override suspend fun insertAsset(assets: List<DatabaseAsset>) {
        inMemoryAssets.value = assets
        if (::findAssetFlow.isInitialized) {
            assets.firstOrNull { it.shortName == findAssetFlow.value.shortName }
                ?.let { findAssetFlow.value = it }
        }
    }
}

class FakeAPIService(private val assets: List<RemoteAsset> = emptyList(), private val error: Any = "") : APIService {

    override suspend fun getAssets(region: String, lang: String, symbols: String) = RemoteResult(
        RemoteAssetResponse(
            error = error,
            result = assets
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
