package dev.bulean.notwallet.ui.detail_asset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.bulean.notwallet.di.AssetName
import dev.bulean.notwallet.domain.Error
import dev.bulean.notwallet.domain.Asset
import dev.bulean.notwallet.framework.toError
import dev.bulean.notwallet.usecases.FindAssetByShortnameUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class DetailAssetViewModel @Inject constructor(
    @AssetName private val assetName: String,
    findAssetByShortnameUseCase: FindAssetByShortnameUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ViewState())
    val state: StateFlow<ViewState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            findAssetByShortnameUseCase(assetName)
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { asset -> _state.update { ViewState(asset = asset) } }

        }
    }

    data class ViewState(val asset: Asset? = null, val error: Error? = null)
}
