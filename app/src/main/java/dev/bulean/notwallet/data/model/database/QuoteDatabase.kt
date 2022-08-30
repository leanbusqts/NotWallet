package dev.bulean.notwallet.data.model.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Quote::class], version = 1, exportSchema = false)
abstract class QuoteDatabase: RoomDatabase() {

    abstract fun quoteDao(): QuoteDao
}