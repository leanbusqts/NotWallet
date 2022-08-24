package dev.bulean.notwallet.ui.detail_quote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.bulean.notwallet.data.model.QuoteResult

@Suppress("UNCHECKED_CAST")
class DetailQuoteViewModelFactory(private val quote: QuoteResult) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailQuoteViewModel(quote) as T
    }
}