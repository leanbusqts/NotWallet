package dev.bulean.notwallet.usecases

import dev.bulean.notwallet.data.QuoteRepository
import dev.bulean.notwallet.domain.Quote
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindQuoteByShortnameUseCase @Inject constructor(private val repository: QuoteRepository) {

    operator fun invoke(name: String): Flow<Quote> = repository.findByShortname(name)
}
