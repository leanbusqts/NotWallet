package dev.bulean.notwallet.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {

    @Query("SELECT * FROM Quote")
    fun getAll(): Flow<List<Quote>>

    @Query("SELECT * FROM Quote WHERE shortName = :shortName")
    fun findByShortname(shortName: String): Flow<Quote>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quotes: List<Quote>)
}
