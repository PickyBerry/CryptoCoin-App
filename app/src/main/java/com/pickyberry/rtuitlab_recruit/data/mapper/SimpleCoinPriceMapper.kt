package com.pickyberry.rtuitlab_recruit.data.mapper

import com.pickyberry.rtuitlab_recruit.data.network.SimpleCoinPriceDto
import com.pickyberry.rtuitlab_recruit.domain.model.SimpleCoinPriceItem

fun SimpleCoinPriceDto.asSimpleCoinPriceItem(): SimpleCoinPriceItem {
    val item = this.prices.entries.first()
    return SimpleCoinPriceItem(
        id = item.key,
        usd = item.value.usd,
        usd24hChange = item.value.usd24hChange,
        rub = item.value.rub,
        rub24hChange = item.value.rub24hChange
    )
}