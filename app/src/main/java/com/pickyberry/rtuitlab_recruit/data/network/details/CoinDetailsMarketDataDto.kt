package com.pickyberry.rtuitlab_recruit.data.network.details

import com.google.gson.annotations.SerializedName

data class CoinDetailsMarketDataDto(
    @SerializedName("current_price") var currentPrice: CoinDetailsCurrentPriceDto? = CoinDetailsCurrentPriceDto(),
    @SerializedName("market_cap") var marketCap: CoinDetailsMarketCapDto? = CoinDetailsMarketCapDto(),
    @SerializedName("fully_diluted_valuation") var fullyDilutedValuation: CoinDetailsValuationDto? = CoinDetailsValuationDto(),
    @SerializedName("total_volume") var totalVolume: CoinDetailsTotalVolumeDto? = CoinDetailsTotalVolumeDto(),
    @SerializedName("total_supply") var totalSupply: Double? = null,
    @SerializedName("max_supply") var maxSupply: Double? = null,
    @SerializedName("circulating_supply") var circulatingSupply: Double? = null,
)
