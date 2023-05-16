package com.pickyberry.rtuitlab_recruit.data.mapper

import com.pickyberry.rtuitlab_recruit.data.database.entity.CoinDetailsEntity
import com.pickyberry.rtuitlab_recruit.data.network.CoinDetailsDto
import com.pickyberry.rtuitlab_recruit.domain.model.CoinDetails
import com.pickyberry.rtuitlab_recruit.domain.model.Currencies
import com.pickyberry.rtuitlab_recruit.domain.model.MarketData


fun CoinDetailsEntity.asCoinDetails() =
    CoinDetails(
        id = this.id,
        name = this.name,
        symbol = this.symbol,
        hashingAlgorithm = this.hashingAlgorithm,
        description = this.description,
        links = this.links,
        image = this.image,
        marketData = MarketData(
            Currencies(this.currentPrice[0], this.currentPrice[1]),
            Currencies(this.marketCap[0], this.marketCap[1]),
            Currencies(this.fullyDilutedValuation?.get(0), this.fullyDilutedValuation?.get(1)),
            Currencies(this.totalVolume[0], this.totalVolume[1]),
            this.totalSupply,
            this.maxSupply,
            this.circulatingSupply

        )
    )

fun CoinDetails.asCoinDetailsEntity() =
    CoinDetailsEntity(
        id = this.id,
        name = this.name,
        symbol = this.symbol,
        hashingAlgorithm = this.hashingAlgorithm ?: "",
        description = this.description,
        links = this.links,
        image = this.image ?: "",
        currentPrice = listOf(this.marketData.currentPrice?.usd, this.marketData.currentPrice?.rub),
        marketCap = listOf(this.marketData.marketCap?.usd, this.marketData.marketCap?.rub),
        fullyDilutedValuation = listOf(this.marketData.fullyDilutedValuation?.usd, this.marketData.fullyDilutedValuation?.rub),
        totalVolume = listOf(this.marketData.totalVolume?.usd, this.marketData.totalVolume?.rub),
        totalSupply = this.marketData.totalSupply,
        maxSupply = this.marketData.maxSupply,
        circulatingSupply = this.marketData.circulatingSupply
        )


fun CoinDetailsDto.asCoinDetails() = CoinDetails(
    id = this.id,
    symbol = this.symbol,
    name = this.name,
    hashingAlgorithm = this.hashingAlgorithm,
    description = listOf(this.description?.en ?: "", this.description?.ru ?: ""),
    links = mapOf(
        Pair("homepage", this.links?.homepage?.getOrNull(0) ?: ""),
        Pair("blockchain_site", this.links?.blockchainSite?.getOrNull(0) ?: "")
    ),
    image = this.image?.large,
    marketData = MarketData(
        currentPrice = Currencies(
            usd = this.marketData?.currentPrice?.usd,
            rub = this.marketData?.currentPrice?.rub
        ),
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
        totalSupply = this.marketData?.totalSupply,
        maxSupply = this.marketData?.maxSupply,
        circulatingSupply = this.marketData?.circulatingSupply,
    )
)