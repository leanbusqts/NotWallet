package dev.bulean.notwallet.usecases

import dev.bulean.notwallet.data.AssetRepository
import javax.inject.Inject

class ClearAssetsUseCase @Inject constructor(private val repository: AssetRepository) {

    suspend operator fun invoke() = repository.clear()
}