package dev.bulean.notwallet.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.bulean.notwallet.model.database.Quote
import dev.bulean.notwallet.model.database.QuoteDao

@Database(entities = [Quote::class], version = 1, exportSchema = false)
abstract class QuoteDatabase: RoomDatabase() {

    abstract fun quoteDao(): QuoteDao
}