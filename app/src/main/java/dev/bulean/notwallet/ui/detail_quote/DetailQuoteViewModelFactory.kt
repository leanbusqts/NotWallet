package dev.bulean.notwallet.ui.detail_quote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.bulean.notwallet.model.QuoteRepository

@Suppress("UNCHECKED_CAST")
class DetailQuoteViewModelFactory(private val quoteName: String, private val repository: QuoteRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailQuoteViewModel(quoteName, repository) as T
    }
}
