package com.pickyberry.rtuitlab_recruit.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CoinItemDao {


    @Insert(onConflict= OnConflictStrategy.REPLACE)
    suspend fun insertCoinItems(
        companyListingEntities:List<CoinItemEntity>
    )

    @Query("DELETE FROM coinitementity")
    suspend fun clearCoins()

    @Query(
        """
            SELECT *
            FROM coinitementity
            WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
            UPPER(:query) == symbol
        """
    )
    suspend fun search(query:String):List<CoinItemEntity>
}