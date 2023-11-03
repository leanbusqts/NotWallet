package dev.bulean.notwallet.data.datasource

import arrow.core.Either
import dev.bulean.notwallet.domain.Asset
import dev.bulean.notwallet.domain.Error

interface AssetRemoteDataSource {
    suspend fun getAssets(region: String, lang: String, symbols: String): Either<Error, List<Asset>>
}
