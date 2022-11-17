package dev.bulean.notwallet.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class RemoteResult(
    val quoteResponse: RemoteQuoteResponse
)

data class RemoteQuoteResponse(
    val error: Any,
    val result: List<RemoteQuote>
)

@Parcelize
data class RemoteQuote(
    val currency: String,
    val regularMarketPrice: Double,
    val shortName: String,
    val symbol: String
) : Parcelable
