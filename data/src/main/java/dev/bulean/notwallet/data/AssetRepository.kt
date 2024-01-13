package dev.bulean.notwallet.data

import dev.bulean.notwallet.data.datasource.AssetLocalDataSource
import dev.bulean.notwallet.data.datasource.AssetRemoteDataSource
import dev.bulean.notwallet.domain.Error
import dev.bulean.notwallet.domain.Asset
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AssetRepository @Inject constructor(
    private val regionRepository: RegionRepository,
    private val localDataSource: AssetLocalDataSource,
    private val remoteDataSource: AssetRemoteDataSource
) {

    val popularAssets = localDataSource.assets

    fun findByShortname(shortName: String): Flow<Asset> = localDataSource.findByShortname(shortName)

    suspend fun getAssets(symbols: String): Error? {
        if (localDataSource.isEmpty()) {
            val region: String
            val lang: String
            when(regionRepository.findLastRegion()) {
                "US" -> {
                    region = "US"
                    lang = "en"
                }
                else -> {
                    region = "US"
                    lang = "en"
                }
            }
            val assets = remoteDataSource.getAssets(region, lang, symbols)
            assets.fold(ifLeft = { return it }) {
                localDataSource.insert(it)
            }
        }
        return null
    }

    suspend fun clear() {
        localDataSource.delete()
    }
}
