package com.pickyberry.rtuitlab_recruit.data.database.dao

import androidx.room.*
import com.pickyberry.rtuitlab_recruit.data.database.entity.CoinDetailsEntity


@Dao
interface CoinDetailsDao {

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    suspend fun insertCoinDetails(
        coinDetailsEntity: CoinDetailsEntity
    )

    @Query("SELECT * FROM coin_details_entity WHERE :id == id OR :id==symbol")
    suspend fun getCoinDetails(id:String): CoinDetailsEntity

}