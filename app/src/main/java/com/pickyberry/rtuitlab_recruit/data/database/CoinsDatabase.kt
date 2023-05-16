package com.pickyberry.rtuitlab_recruit.data.database

import androidx.room.*
import com.pickyberry.rtuitlab_recruit.data.database.dao.CoinDetailsDao
import com.pickyberry.rtuitlab_recruit.data.database.dao.CoinItemDao
import com.pickyberry.rtuitlab_recruit.data.database.dao.HistoricalDataDao
import com.pickyberry.rtuitlab_recruit.data.database.entity.CoinDetailsEntity
import com.pickyberry.rtuitlab_recruit.data.database.entity.CoinItemEntity
import com.pickyberry.rtuitlab_recruit.data.database.entity.HistoricalDataEntity

@Database(
    entities = [CoinItemEntity::class,CoinDetailsEntity::class,HistoricalDataEntity::class],
    version = 8,
    exportSchema = true
)
@TypeConverters(RoomConverter::class)
abstract class CoinsDatabase : RoomDatabase() {
    abstract val coinItemDao: CoinItemDao
    abstract val coinDetailsDao: CoinDetailsDao
    abstract val historicalDataDao: HistoricalDataDao
}