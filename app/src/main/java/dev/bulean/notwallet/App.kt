package dev.bulean.notwallet

import android.app.Application
import androidx.room.Room
import dev.bulean.notwallet.framework.database.QuoteDatabase

class App : Application() {

    lateinit var database: QuoteDatabase
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
