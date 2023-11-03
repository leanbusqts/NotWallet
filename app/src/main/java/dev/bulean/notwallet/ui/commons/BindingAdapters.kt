package dev.bulean.notwallet.ui.commons

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.bulean.notwallet.domain.Asset
import dev.bulean.notwallet.ui.main.AssetsAdapter

@BindingAdapter("visible")
fun View.setVisibility(visible: Boolean?) {
    visibility = if (visible == true) View.VISIBLE else View.GONE
}

@BindingAdapter("items")
fun RecyclerView.setItems(assets: List<Asset>?) {
    if (assets != null) {
        (adapter as? AssetsAdapter)?.submitList(assets)
    }
}
