package dev.bulean.notwallet.ui.detail_quote

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.bulean.notwallet.domain.Error
import dev.bulean.notwallet.domain.Quote
import dev.bulean.notwallet.framework.toError
import dev.bulean.notwallet.usecases.FindQuoteByShortnameUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class DetailQuoteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    findQuoteByShortnameUseCase: FindQuoteByShortnameUseCase
) : ViewModel() {

    private val name = DetailQuoteFragmentArgs.fromSavedStateHandle(savedStateHandle).quoteName
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
