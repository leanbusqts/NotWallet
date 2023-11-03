package dev.bulean.notwallet.framework.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetDao {

    @Query("SELECT * FROM Asset")
    fun getAll(): Flow<List<Asset>>

    @Query("SELECT COUNT(shortName) FROM Asset")
    suspend fun assetCount(): Int

    @Query("SELECT * FROM Asset WHERE shortName = :shortName")
    fun findByShortname(shortName: String): Flow<Asset>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsset(assets: List<Asset>)
}
