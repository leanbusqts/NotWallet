package dev.bulean.notwallet.ui.detail_quote

import androidx.lifecycle.SavedStateHandle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.bulean.notwallet.di.QuoteName

@Module
@InstallIn(ViewModelComponent::class)
class DetailQuoteViewModelModule {

    @Provides
    @ViewModelScoped
    @QuoteName
    fun provideQuoteName(savedStateHandle: SavedStateHandle) =
        DetailQuoteFragmentArgs.fromSavedStateHandle(savedStateHandle).quoteName
}
