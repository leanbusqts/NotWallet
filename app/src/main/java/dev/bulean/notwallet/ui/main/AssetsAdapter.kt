package dev.bulean.notwallet.ui.main

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.bulean.notwallet.R
import dev.bulean.notwallet.databinding.AssetLayoutBinding
import dev.bulean.notwallet.domain.Asset

class AssetsAdapter(private val listener: (Asset) -> Unit) :
    ListAdapter<Asset, AssetsAdapter.ViewHolder>(ItemsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.asset_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = AssetLayoutBinding.bind(view)

        fun bind(asset: Asset) {
            binding.asset = asset
            val imageView = binding.arrow
            val regularMarketPreviousClose = asset.regularMarketPreviousClose
            val regularMarketPrice = asset.regularMarketPrice
            when {
                regularMarketPreviousClose > regularMarketPrice -> {
                    imageView.visibility = View.VISIBLE
                    imageView.setImageDrawable(
                        AppCompatResources.getDrawable(
                            imageView.context,
                            R.drawable.arrow_down
                        )
                    )
                    imageView.setColorFilter(
                        ContextCompat.getColor(
                            imageView.context,
                            R.color.arrow_red
                        ),
                        PorterDuff.Mode.SRC_ATOP
                    )
                }
                regularMarketPreviousClose < regularMarketPrice -> {
                    imageView.visibility = View.VISIBLE
                    imageView.setImageDrawable(
                        AppCompatResources.getDrawable(
                            imageView.context,
                            R.drawable.arrow_up
                        )
                    )
                    imageView.setColorFilter(
                        ContextCompat.getColor(
                            imageView.context,
                            R.color.arrow_green
                        ),
                        PorterDuff.Mode.SRC_ATOP
                    )
                }
                else -> {
                    imageView.visibility = View.GONE
                }
            }
        }
    }

    class ItemsDiffCallback : DiffUtil.ItemCallback<Asset>() {
        override fun areItemsTheSame(oldItem: Asset, newItem: Asset): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(oldItem: Asset, newItem: Asset): Boolean {
            return oldItem == newItem
        }
    }
}
