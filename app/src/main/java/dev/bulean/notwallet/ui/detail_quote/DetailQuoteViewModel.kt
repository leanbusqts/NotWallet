package dev.bulean.notwallet.ui.detail_quote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bulean.notwallet.model.Error
import dev.bulean.notwallet.model.QuoteRepository
import dev.bulean.notwallet.model.database.Quote
import dev.bulean.notwallet.model.toError
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailQuoteViewModel(
    name: String,
    private val repository: QuoteRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ViewState())
    val state: StateFlow<ViewState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.findByShortname(name)
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { quote -> _state.update { ViewState(quote = quote) } }

        }
    }

    data class ViewState(val quote: Quote? = null, val error: Error? = null)
}
