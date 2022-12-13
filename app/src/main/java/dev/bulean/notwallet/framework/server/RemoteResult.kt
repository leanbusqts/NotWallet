package dev.bulean.notwallet.framework.server

data class RemoteResult(
    val quoteResponse: RemoteQuoteResponse
)

data class RemoteQuoteResponse(
    val error: Any,
    val result: List<RemoteQuote>
)

data class RemoteQuote(
    val currency: String,
    val regularMarketPrice: Double,
    val shortName: String,
    val symbol: String
)
