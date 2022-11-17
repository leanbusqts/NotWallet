package dev.bulean.notwallet.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.bulean.notwallet.model.QuoteRepository

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val repository: QuoteRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}
