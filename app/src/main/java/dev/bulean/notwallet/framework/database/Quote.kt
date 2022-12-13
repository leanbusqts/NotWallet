package dev.bulean.notwallet.framework.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Quote(
    val currency: String,
    val regularMarketPrice: Double,
    @PrimaryKey(autoGenerate = false)
    val shortName: String,
    val symbol: String
)
