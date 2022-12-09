package dev.bulean.notwallet.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.bulean.notwallet.usecases.GetQuotesUseCase
import dev.bulean.notwallet.usecases.PopularQuotesUseCase

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val popularQuotesUseCase: PopularQuotesUseCase,
    private val getQuotesUseCase: GetQuotesUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            popularQuotesUseCase,
            getQuotesUseCase
        ) as T
    }
}
