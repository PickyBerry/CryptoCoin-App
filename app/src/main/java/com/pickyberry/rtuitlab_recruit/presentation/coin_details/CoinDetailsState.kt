package com.pickyberry.rtuitlab_recruit.presentation.coin_details

import com.pickyberry.rtuitlab_recruit.domain.model.CoinDetails

data class CoinDetailsState(
    val historicalData: List<List<Float>> = listOf(),
    val coinDetails: CoinDetails? = null,
    val isLoading: Boolean = false,
)
