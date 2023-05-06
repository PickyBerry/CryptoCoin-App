package com.pickyberry.rtuitlab_recruit.data.database

import androidx.room.*
import androidx.room.migration.AutoMigrationSpec

@Database(
    entities = [CoinItemEntity::class],
    version = 2,
    autoMigrations = [
        AutoMigration (
            from = 1,
            to = 2,
            spec = CoinsDatabase.CoinAutoMigration::class
        )
    ],
    exportSchema = true
)
abstract class CoinsDatabase : RoomDatabase() {
    abstract val dao: CoinItemDao

    @DeleteColumn(tableName="CoinItemEntity",columnName = "rank")
    class CoinAutoMigration : AutoMigrationSpec
}