package dev.bulean.notwallet.ui.detail_quote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bulean.notwallet.domain.Error
import dev.bulean.notwallet.domain.Quote
import dev.bulean.notwallet.domain.toError
import dev.bulean.notwallet.usecases.FindQuoteByShortnameUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailQuoteViewModel(
    name: String,
    private val findQuoteByShortnameUseCase: FindQuoteByShortnameUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ViewState())
    val state: StateFlow<ViewState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            findQuoteByShortnameUseCase(name)
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { quote -> _state.update { ViewState(quote = quote) } }

        }
    }

    data class ViewState(val quote: Quote? = null, val error: Error? = null)
}
