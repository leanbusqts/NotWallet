package dev.bulean.notwallet.ui.commons

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.bulean.notwallet.model.database.Quote
import dev.bulean.notwallet.ui.main.QuotesAdapter

@BindingAdapter("visible")
fun View.setVisibility(visible: Boolean?) {
    visibility = if (visible == true) View.VISIBLE else View.GONE
}

@BindingAdapter("items")
fun RecyclerView.setItems(quotes: List<Quote>?){
    if (quotes != null) {
        (adapter as? QuotesAdapter)?.submitList(quotes)
    }
}