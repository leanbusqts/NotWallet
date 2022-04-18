package dev.bulean.notwallet.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.bulean.notwallet.R
import dev.bulean.notwallet.data.model.QuoteResult
import dev.bulean.notwallet.databinding.QuoteLayoutBinding

class QuotesAdapter (
    private val onClickListener: (QuoteResult) -> Unit)
    : ListAdapter<QuoteResult, QuotesAdapter.ItemViewHolder>(ItemsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(layoutInflater.inflate(R.layout.quote_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickListener)
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = QuoteLayoutBinding.bind(view)

        fun bind(quote: QuoteResult, onClickListener: (QuoteResult) -> Unit) {
            binding.apply {
                shortName.text = quote.shortName
                symbol.text = quote.symbol
                regularMarketPrice.text = quote.regularMarketPrice.toString()
            }
            itemView.setOnClickListener { onClickListener(quote) }
        }
    }

    class ItemsDiffCallback : DiffUtil.ItemCallback<QuoteResult>() {
        override fun areItemsTheSame(oldItem: QuoteResult, newItem: QuoteResult): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(oldItem: QuoteResult, newItem: QuoteResult): Boolean {
            return oldItem == newItem
        }

    }
}