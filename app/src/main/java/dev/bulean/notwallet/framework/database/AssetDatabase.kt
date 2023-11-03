package dev.bulean.notwallet.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Asset::class], version = 3, exportSchema = false)
abstract class AssetDatabase : RoomDatabase() {

    abstract fun assetDao(): AssetDao
}
