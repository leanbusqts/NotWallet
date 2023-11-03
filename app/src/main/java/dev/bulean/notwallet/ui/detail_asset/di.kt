package dev.bulean.notwallet.ui.detail_asset

import androidx.lifecycle.SavedStateHandle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.bulean.notwallet.di.AssetName

@Module
@InstallIn(ViewModelComponent::class)
class DetailAssetViewModelModule {

    @Provides
    @ViewModelScoped
    @AssetName
    fun provideAssetName(savedStateHandle: SavedStateHandle) =
        DetailAssetFragmentArgs.fromSavedStateHandle(savedStateHandle).assetName
}
