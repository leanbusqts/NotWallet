package dev.bulean.notwallet.usecases

import dev.bulean.notwallet.data.AssetRepository
import dev.bulean.notwallet.domain.Asset
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindAssetByShortnameUseCase @Inject constructor(private val repository: AssetRepository) {

    operator fun invoke(name: String): Flow<Asset> = repository.findByShortname(name)
}
