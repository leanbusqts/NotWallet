package dev.bulean.notwallet

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.bulean.notwallet.data.model.database.QuoteDatabase

class App : Application() {

    lateinit var database: RoomDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            this,
            QuoteDatabase::class.java,
            "quote-database"
        ).build()
    }
}