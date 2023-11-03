package dev.bulean.notwallet.usecases

import dev.bulean.notwallet.data.AssetRepository
import dev.bulean.notwallet.domain.Asset
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class PopularAssetsUseCase @Inject constructor(private val repository: AssetRepository) {

    operator fun invoke(): Flow<List<Asset>> = repository.popularAssets
}
