package dev.bulean.notwallet.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.bulean.notwallet.BuildConfig
import dev.bulean.notwallet.data.APIService
import dev.bulean.notwallet.data.PermissionChecker
import dev.bulean.notwallet.data.datasource.LocationDataSource
import dev.bulean.notwallet.data.datasource.AssetLocalDataSource
import dev.bulean.notwallet.data.datasource.AssetRemoteDataSource
import dev.bulean.notwallet.framework.AndroidPermissionChecker
import dev.bulean.notwallet.framework.PlayServicesLocationDataSource
import dev.bulean.notwallet.framework.database.AssetDatabase
import dev.bulean.notwallet.framework.database.AssetRoomDataSource
import dev.bulean.notwallet.framework.server.AssetServerDataSource
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences("my_shared_preferences", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.databaseBuilder(
        app,
        AssetDatabase::class.java,
        "asset-db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideAssetDao(db: AssetDatabase) = db.assetDao()

    @Provides
    @Singleton
    fun provideRemoteService(): APIService {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val headerInterceptor = Interceptor {
            val request = it.request().newBuilder().addHeader(BuildConfig.APIKEY, BuildConfig.APIVALUE)
                .build()
            return@Interceptor it.proceed(request)
        }

        val okHttpClient = HttpLoggingInterceptor().run {
            OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()
        }

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASEURL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule {

    @Binds
    abstract fun bindLocalDataSource(localDataSource: AssetRoomDataSource): AssetLocalDataSource

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: AssetServerDataSource): AssetRemoteDataSource

    @Binds
    abstract fun bindLocationDataSource(locationDataSource: PlayServicesLocationDataSource): LocationDataSource

    @Binds
    abstract fun bindPermissionChecker(permissionChecker: AndroidPermissionChecker): PermissionChecker
}
