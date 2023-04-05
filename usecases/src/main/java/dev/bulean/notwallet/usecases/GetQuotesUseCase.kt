package dev.bulean.notwallet.usecases

import dev.bulean.notwallet.data.QuoteRepository
import dev.bulean.notwallet.domain.Error
import javax.inject.Inject

class GetQuotesUseCase @Inject constructor(private val repository: QuoteRepository) {

    suspend operator fun invoke(): Error? {
        return repository.getQuotes( "GOOGL,AAPL,BTC-USD,ETH-USD,MELI,AMZN,TSLA")
    }
}
