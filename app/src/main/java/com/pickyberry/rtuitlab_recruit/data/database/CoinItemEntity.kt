package com.pickyberry.rtuitlab_recruit.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pickyberry.rtuitlab_recruit.domain.model.CoinItem

@Entity
data class CoinItemEntity(
    @PrimaryKey val id:String,
    val name: String,
    val symbol: String,
    val rank: Int,
)

