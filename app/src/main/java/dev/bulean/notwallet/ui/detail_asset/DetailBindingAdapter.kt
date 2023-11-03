package dev.bulean.notwallet.ui.detail_asset

import androidx.databinding.BindingAdapter
import dev.bulean.notwallet.domain.Asset

@BindingAdapter("asset")
fun AssetDetailInfoView.updateAssetDetails(asset: Asset?) {
    if (asset != null) {
        setAsset(asset)
    }
}