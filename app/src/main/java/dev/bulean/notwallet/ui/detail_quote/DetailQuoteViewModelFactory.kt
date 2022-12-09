package dev.bulean.notwallet.ui.detail_quote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.bulean.notwallet.usecases.FindQuoteByShortnameUseCase

@Suppress("UNCHECKED_CAST")
class DetailQuoteViewModelFactory(
    private val quoteName: String,
    private val findQuoteByShortnameUseCase: FindQuoteByShortnameUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailQuoteViewModel(
            quoteName,
            findQuoteByShortnameUseCase
        ) as T
    }
}
