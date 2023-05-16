package com.pickyberry.rtuitlab_recruit.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pickyberry.rtuitlab_recruit.data.database.entity.CoinItemEntity

@Dao
interface CoinItemDao {


    @Insert(onConflict= OnConflictStrategy.REPLACE)
    suspend fun insertCoinItems(
        coinItemEntities:List<CoinItemEntity>
    )

    @Query("DELETE FROM coin_item_entity")
    suspend fun clearCoins()

    @Query(
        """
            SELECT *
            FROM coin_item_entity
            WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
            UPPER(:query) == symbol
        """
    )
    suspend fun search(query:String):List<CoinItemEntity>

    @Query("UPDATE coin_item_entity SET isFavorite = :isFavorite WHERE id = :itemId")
    suspend fun updateFavoriteStatus(id: String, isFavorite: Boolean)
}