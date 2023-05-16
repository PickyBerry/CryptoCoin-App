package com.pickyberry.rtuitlab_recruit.data

import com.pickyberry.rtuitlab_recruit.data.database.entity.CoinItemEntity
import com.pickyberry.rtuitlab_recruit.data.network.CoinDto
import com.pickyberry.rtuitlab_recruit.domain.model.CoinItem

fun CoinItemEntity.asCoinItem() =
    CoinItem(
        id = this.id,
        name = this.name,
        symbol = this.symbol,
        image = this.image,
        currentPrice = this.currentPrice,
        priceChangePercentage = this.priceChangePercentage,
        marketCapChangePercentage = this.marketCapChangePercentage,
        isFavorite=this.isFavorite
    )

fun CoinItem.asCoinItemEntity() =
    CoinItemEntity(
        id = this.id,
        name = this.name,
        symbol = this.symbol,
        image = this.image,
        currentPrice = this.currentPrice,
        priceChangePercentage = this.priceChangePercentage,
        marketCapChangePercentage = this.marketCapChangePercentage
    )

fun CoinDto.asCoinItem() =
    CoinItem(
        id = this.id,
        name = this.name,
        symbol = this.symbol.uppercase(),
        image = this.image,
        currentPrice = this.currentPrice,
        priceChangePercentage = this.priceChangePercentage,
        marketCapChangePercentage = this.marketCapChangePercentage,
        isFavorite = false
    )