package com.pickyberry.rtuitlab_recruit.domain.model


data class CoinItem(
    val id:String,
    val name:String,
    val symbol:String,
    val image:String,
    val currentPrice: Float,
    val priceChangePercentage: Float,
    val marketCapChangePercentage: Float,
    val isFavorite:Boolean
)
