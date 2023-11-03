package dev.bulean.notwallet.framework.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Asset(
    val currency: String,
    val regularMarketPrice: Double,
    @PrimaryKey(autoGenerate = false)
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
