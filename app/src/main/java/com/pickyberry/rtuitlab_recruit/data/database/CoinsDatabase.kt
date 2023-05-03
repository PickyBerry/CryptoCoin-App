package com.pickyberry.rtuitlab_recruit.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CoinItemEntity::class],
    version = 1
)
abstract class CoinsDatabase : RoomDatabase() {
    abstract val dao: CoinItemDao
}