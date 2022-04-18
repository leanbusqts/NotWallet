package dev.bulean.notwallet.ui

import android.util.Log
import androidx.lifecycle.*
import dev.bulean.notwallet.data.model.QuoteResponse
import dev.bulean.notwallet.data.model.QuoteResult
import dev.bulean.notwallet.data.repository.QuoteRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: QuoteRepository = QuoteRepository()) : ViewModel(){

    private var _listItem = MutableLiveData<List<QuoteResult>>()
    val listItem: LiveData<List<QuoteResult>> get() = _listItem

    fun onAction() {

        viewModelScope.launch {
            try {
                val response = repository.getQuotes("US", "en", "AAPL%2CBTC-USD")
                _listItem.postValue(response)
            } catch (e: Exception) {
                Log.e("onAction", "$e")
            }
        }

    }


}