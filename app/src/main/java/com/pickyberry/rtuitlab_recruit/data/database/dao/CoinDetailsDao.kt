package com.pickyberry.rtuitlab_recruit.data.database.dao

import androidx.room.*
import com.pickyberry.rtuitlab_recruit.data.database.entity.CoinDetailsEntity


@Dao
interface CoinDetailsDao {

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    suspend fun insertCoinDetails(
        coinDetailsEntity: CoinDetailsEntity
    )

    @Update
    suspend fun updateCoinDetails(coinDetailsEntity: CoinDetailsEntity)

    @Query("SELECT * FROM coin_details_entity WHERE :id == id")
    suspend fun getCoinDetails(id:String): CoinDetailsEntity

}