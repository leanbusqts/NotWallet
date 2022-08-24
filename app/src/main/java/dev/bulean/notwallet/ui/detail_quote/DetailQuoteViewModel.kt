package dev.bulean.notwallet.ui.detail_quote

import androidx.lifecycle.ViewModel
import dev.bulean.notwallet.data.model.QuoteResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailQuoteViewModel(quote: QuoteResult) : ViewModel() {

    class ViewState(val quote: QuoteResult)

    private val _state = MutableStateFlow(ViewState(quote))
    val state: StateFlow<ViewState> = _state.asStateFlow()
}
