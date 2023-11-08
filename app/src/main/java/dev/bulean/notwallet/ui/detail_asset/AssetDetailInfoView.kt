package dev.bulean.notwallet.ui.detail_asset

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import dev.bulean.notwallet.domain.Asset

class AssetDetailInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    fun setAsset(asset: Asset) = asset.apply {
        text = buildSpannedString {

            bold { append("Currency: ") }
            appendLine(currency)

            bold { append("Regular Market Price: ") }
            appendLine(regularMarketPrice.toString())

            bold { append("Market Cap: ") }
            appendLine(marketCap.toString())

            bold { append("Regular Market Change: ") }
            appendLine(regularMarketChange.toString())

            bold { append("Regular Market Previous Close: ") }
            appendLine(regularMarketPreviousClose.toString())

            bold { append("Regular Market Change Percent: ") }
            appendLine(regularMarketChangePercent.toString())

            bold { append("Regular Market Volume: ") }
            appendLine(regularMarketVolume.toString())

            bold { append("Regular Market Day Range:\n") }
            append(regularMarketDayRange)
        }
    }
}
