package dev.bulean.notwallet.data.datasource

import dev.bulean.notwallet.domain.Error
import dev.bulean.notwallet.domain.Asset
import kotlinx.coroutines.flow.Flow

interface AssetLocalDataSource {
    val assets: Flow<List<Asset>>
    suspend fun isEmpty(): Boolean
    fun findByShortname(shortName: String): Flow<Asset>

    suspend fun insert(asset: List<Asset>): Error?
}
