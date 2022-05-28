package dev.bulean.notwallet.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import dev.bulean.notwallet.data.model.QuoteResult
import dev.bulean.notwallet.data.repository.QuoteRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application, private val repository: QuoteRepository = QuoteRepository()) : AndroidViewModel(application){

    private var _state = MutableLiveData<ViewState>()
    val state: LiveData<ViewState> get() = _state

    private var _listItem = MutableLiveData<List<QuoteResult>>()
    val listItem: LiveData<List<QuoteResult>> get() = _listItem

    init {
        onAction()
    }

    private fun onAction() {
        _state.value = ViewState.Loading
        viewModelScope.launch {
            try {
                val response = repository.getQuotes("US", "en", "AAPL%2CBTC-USD")
                _listItem.postValue(response)
                _state.postValue(ViewState.Loaded)
            } catch (e: Exception) {
                _state.postValue(ViewState.Error)
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