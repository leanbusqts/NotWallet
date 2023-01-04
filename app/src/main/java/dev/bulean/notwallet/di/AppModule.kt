package dev.bulean.notwallet.di

import android.app.Application
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.bulean.notwallet.data.datasource.QuoteLocalDataSource
import dev.bulean.notwallet.data.datasource.QuoteRemoteDataSource
import dev.bulean.notwallet.framework.database.QuoteDatabase
import dev.bulean.notwallet.framework.database.QuoteRoomDataSource
import dev.bulean.notwallet.framework.server.QuoteServerDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.databaseBuilder(
        app,
        QuoteDatabase::class.java,
        "quote-db"
    ).build()

    @Provides
    @Singleton
    fun provideQuoteDao(db: QuoteDatabase) = db.quoteDao()

}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule {

    @Binds
    abstract fun bindLocalDataSource(localDataSource: QuoteRoomDataSource): QuoteLocalDataSource

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: QuoteServerDataSource): QuoteRemoteDataSource

}