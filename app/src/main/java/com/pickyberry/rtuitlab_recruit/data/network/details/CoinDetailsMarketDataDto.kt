package com.pickyberry.rtuitlab_recruit.data.network.details

import com.google.gson.annotations.SerializedName

data class CoinDetailsMarketDataDto(

    @SerializedName("current_price") var currentPrice: CoinDetailsCurrentPriceDto? = CoinDetailsCurrentPriceDto(),
    @SerializedName("total_value_locked") var totalValueLocked: String? = null,
    @SerializedName("market_cap") var marketCap: CoinDetailsMarketCapDto? = CoinDetailsMarketCapDto(),
    @SerializedName("fully_diluted_valuation") var fullyDilutedValuation: CoinDetailsValuationDto? = CoinDetailsValuationDto(),
    @SerializedName("total_volume") var totalVolume: CoinDetailsTotalVolumeDto? = CoinDetailsTotalVolumeDto(),
    @SerializedName("total_supply") var totalSupply: Int? = null,
    @SerializedName("max_supply") var maxSupply: Int? = null,
    @SerializedName("circulating_supply") var circulatingSupply: Int? = null,
    @SerializedName("last_updated") var lastUpdated: String? = null

)
