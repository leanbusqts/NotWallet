package dev.bulean.notwallet.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import dev.bulean.notwallet.data.model.QuoteResult
import dev.bulean.notwallet.data.repository.QuoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application, private val repository: QuoteRepository = QuoteRepository()) : AndroidViewModel(application){

    private var _state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
    val state: StateFlow<ViewState> get() = _state.asStateFlow()

    private var _listItem: MutableStateFlow<List<QuoteResult>> = MutableStateFlow(emptyList())
    val listItem: StateFlow<List<QuoteResult>> get() = _listItem

    init {
        onAction()
    }

    private fun onAction() {
        _state.value = ViewState.Loading
        viewModelScope.launch {
            try {
                val response = repository.getQuotes("US", "en", "GOOGL%2CAAPL%2CBTC-USD%2CETH-USD%2CMELI%2CAMZN%2CTSLA") // %2CBTC-EUR%2CETH-EUR
                _listItem.value = response
                _state.value = ViewState.Loaded
            } catch (e: Exception) {
                _state.value = ViewState.Error
                Log.e("onAction", "$e")
            }
        }
    }

    sealed class ViewState {
        object Loading : ViewState()
        object Loaded : ViewState()
        object Error : ViewState()
    }
}