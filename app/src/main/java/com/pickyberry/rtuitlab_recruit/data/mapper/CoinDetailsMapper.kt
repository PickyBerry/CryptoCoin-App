package com.pickyberry.rtuitlab_recruit.data.mapper

import com.pickyberry.rtuitlab_recruit.data.network.CoinDetailsDto
import com.pickyberry.rtuitlab_recruit.domain.model.CoinDetails
import com.pickyberry.rtuitlab_recruit.domain.model.Currencies
import com.pickyberry.rtuitlab_recruit.domain.model.MarketData

fun CoinDetailsDto.asCoinDetails() = CoinDetails(
    id = this.id,
    symbol = this.symbol,
    name = this.name,
    hashingAlgorithm = this.hashingAlgorithm,
    description = listOf(this.description?.en ?: "", this.description?.ru ?: ""),
    links = listOf(
        this.links?.homepage?.getOrNull(0) ?: "",
        this.links?.blockchainSite?.getOrNull(0) ?: ""
    ),
    image = this.image?.large,
    marketData = MarketData(
        currentPrice = Currencies(
            usd = this.marketData?.currentPrice?.usd,
            rub = this.marketData?.currentPrice?.rub
        ),
        totalValueLocked = this.marketData?.totalValueLocked,
        marketCap = Currencies(
            usd = this.marketData?.marketCap?.usd,
            rub = this.marketData?.marketCap?.rub
        ),
        fullyDilutedValuation = Currencies(
            usd = this.marketData?.fullyDilutedValuation?.usd,
            rub = this.marketData?.fullyDilutedValuation?.rub
        ),
        totalVolume = Currencies(
            usd = this.marketData?.totalVolume?.usd,
            rub = this.marketData?.totalVolume?.rub
        ),
     //   totalSupply = this.marketData?.totalSupply,
        maxSupply = this.marketData?.maxSupply,
        circulatingSupply = this.marketData?.circulatingSupply,
    )
)