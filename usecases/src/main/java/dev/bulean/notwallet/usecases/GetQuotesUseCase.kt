package dev.bulean.notwallet.usecases

import dev.bulean.notwallet.data.QuoteRepository
import javax.inject.Inject

class GetQuotesUseCase @Inject constructor(private val repository: QuoteRepository) {

    suspend operator fun invoke() = repository.getQuotes("US", "en", "GOOGL%2CAAPL%2CBTC-USD%2CETH-USD%2CMELI%2CAMZN%2CTSLA")
}