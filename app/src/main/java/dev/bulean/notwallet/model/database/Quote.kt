package dev.bulean.notwallet.model.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Quote(
    val currency: String,
    val regularMarketPrice: Double,
    @PrimaryKey(autoGenerate = false)
    val shortName: String,
    val symbol: String
) : Parcelable
