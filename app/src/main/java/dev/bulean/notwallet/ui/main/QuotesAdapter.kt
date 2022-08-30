package dev.bulean.notwallet.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.bulean.notwallet.R
import dev.bulean.notwallet.data.model.QuoteResult
import dev.bulean.notwallet.databinding.QuoteLayoutBinding

class QuotesAdapter (private val listener: (QuoteResult) -> Unit) :
    ListAdapter<QuoteResult, QuotesAdapter.ViewHolder>(ItemsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.quote_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = QuoteLayoutBinding.bind(view)

        fun bind(quote: QuoteResult) {
            binding.quote = quote
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
