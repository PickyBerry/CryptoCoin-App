package com.pickyberry.rtuitlab_recruit.data

import com.pickyberry.rtuitlab_recruit.data.database.CoinItemEntity
import com.pickyberry.rtuitlab_recruit.data.network.CoinDto
import com.pickyberry.rtuitlab_recruit.domain.model.CoinItem

fun CoinItemEntity.asCoinItem() =
    CoinItem(id = this.id, name = this.name, symbol = this.symbol, rank = this.rank)

fun CoinItem.asCoinItemEntity() =
    CoinItemEntity(id = this.id, name = this.name, symbol = this.symbol, rank = this.rank)

fun CoinDto.asCoinItem() =
    CoinItem(id = this.id, name = this.name, symbol = this.symbol, rank = this.rank)