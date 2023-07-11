package com.pickyberry.rtuitlab_recruit.data.database.dao

import androidx.room.*
import com.pickyberry.rtuitlab_recruit.data.database.entity.CoinItemEntity

@Dao
interface CoinItemDao {


    @Insert(onConflict= OnConflictStrategy.REPLACE)
    suspend fun insertCoinItems(
        coinItemEntities:List<CoinItemEntity>
    )

    @Query("SELECT COUNT(*) FROM coin_item_entity")
    suspend fun countCoinItems(): Int

    @Update
    suspend fun updateCoins(coinItemEntities: List<CoinItemEntity>)

    @Query(
        """
            SELECT *
            FROM coin_item_entity
            WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
            symbol LIKE '%' || UPPER(:query) || '%'
        """
    )
    suspend fun search(query:String):List<CoinItemEntity>

    @Query("UPDATE coin_item_entity SET isFavorite = NOT isFavorite WHERE id = :id")
    fun toggleCoinFavorite(id: String)

    @Query("SELECT isFavorite FROM coin_item_entity WHERE id = :id")
    fun isCoinFavorite(id: String): Boolean?

    @Query(
        """
            SELECT *
            FROM coin_item_entity
            WHERE (LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
            UPPER(:query) == symbol) AND (isFavorite == true)
        """
    )
    suspend fun searchFavorites(query:String):List<CoinItemEntity>
}