package dev.bulean.notwallet.framework.server

data class RemoteResult(
    val quoteResponse: RemoteAssetResponse
)

data class RemoteAssetResponse(
    val error: Any,
    val result: List<RemoteAsset>
)

data class RemoteAsset(
    val currency: String,
    val regularMarketPrice: Double,
    val shortName: String,
    val symbol: String,
    val regularMarketChange: Double,
    val regularMarketChangePercent: Double,
    val regularMarketVolume: Long,
    val regularMarketDayRange: String,
    val marketCap: Long,
    val fiftyDayAverage: Double,
    val twoHundredDayAverage: Double,
    val trailingPE: Double,
    val trailingAnnualDividendRate: Double,
    val trailingAnnualDividendYield: Double
)
