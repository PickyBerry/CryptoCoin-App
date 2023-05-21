package com.pickyberry.rtuitlab_recruit.data.database.dao

import androidx.room.*
import com.pickyberry.rtuitlab_recruit.data.database.entity.HistoricalDataEntity

@Dao
interface HistoricalDataDao {
    @Insert(onConflict= OnConflictStrategy.REPLACE)
    suspend fun insertHistoricalData(
        historicalDataEntity: HistoricalDataEntity
    )

    @Query("SELECT * FROM historical_data_entity WHERE :id == id")
    suspend fun getHistoricalData(id:String): HistoricalDataEntity
}