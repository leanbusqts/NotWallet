package dev.bulean.notwallet.usecases

import dev.bulean.notwallet.data.QuoteRepository
import dev.bulean.notwallet.domain.Quote
import kotlinx.coroutines.flow.Flow

class PopularQuotesUseCase(private val repository: QuoteRepository) {

    operator fun invoke(): Flow<List<Quote>> = repository.popularQuotes
}