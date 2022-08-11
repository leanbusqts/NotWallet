package dev.bulean.notwallet.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import dev.bulean.notwallet.data.model.QuoteResult
import dev.bulean.notwallet.data.repository.QuoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application,
    private val repository: QuoteRepository = QuoteRepository()
) : AndroidViewModel(application) {

    private var _state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val state: StateFlow<ViewState> get() = _state.asStateFlow()

    init {
        onAction()
    }

    private fun onAction() {
        _state.value = ViewState(loading = true)
        viewModelScope.launch {
            try {
                val response =
                    repository.getQuotes(
                        "US",
                        "en",
                        "GOOGL%2CAAPL%2CBTC-USD%2CETH-USD%2CMELI%2CAMZN%2CTSLA"
                    ) // %2CBTC-EUR%2CETH-EUR
                _state.value = ViewState(quotes = response)
            } catch (e: Exception) {
                _state.value = ViewState(error = true)
                Log.e("onAction", "$e")
            }
        }
    }

    data class ViewState(
        val loading: Boolean = false,
        val quotes: List<QuoteResult>? = null,
        val error: Boolean = false
    )
}