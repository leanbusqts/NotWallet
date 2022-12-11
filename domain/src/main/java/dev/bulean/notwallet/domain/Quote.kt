package dev.bulean.notwallet.domain

data class Quote(
    val currency: String,
    val regularMarketPrice: Double,
    val shortName: String,
    val symbol: String
)
