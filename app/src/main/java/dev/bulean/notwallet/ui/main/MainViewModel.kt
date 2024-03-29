package dev.bulean.notwallet.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.bulean.notwallet.domain.Error
import dev.bulean.notwallet.domain.Quote
import dev.bulean.notwallet.framework.toError
import dev.bulean.notwallet.usecases.GetQuotesUseCase
import dev.bulean.notwallet.usecases.PopularQuotesUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    popularQuotesUseCase: PopularQuotesUseCase,
    private val getQuotesUseCase: GetQuotesUseCase
) : ViewModel() {

    private var _state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val state: StateFlow<ViewState> get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            popularQuotesUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { quotes -> _state.value = ViewState(quotes = quotes) }
        }
    }

    fun onAction() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            //val response = repository.getQuotes("US", "en", "GOOGL%2CAAPL%2CBTC-USD%2CETH-USD%2CMELI%2CAMZN%2CTSLA") // %2CBTC-EUR%2CETH-EUR
            //val error = repository.getQuotes() // %2CBTC-EUR%2CETH-EUR
            val error = getQuotesUseCase()
            _state.update { _state.value.copy(loading = false, error = error) }
        }
    }

    data class ViewState(
        val loading: Boolean = false,
        val quotes: List<Quote>? = null,
        val error: Error? = null
    )
}
