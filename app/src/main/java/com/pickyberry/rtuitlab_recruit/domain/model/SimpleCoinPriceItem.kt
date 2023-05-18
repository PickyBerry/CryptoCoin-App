package com.pickyberry.rtuitlab_recruit.domain.model


data class SimpleCoinPriceItem(
    val id:String,
    val usd: Double?,
    val usd24hChange: Double?,
    val rub: Double?,
    val rub24hChange: Double?
)