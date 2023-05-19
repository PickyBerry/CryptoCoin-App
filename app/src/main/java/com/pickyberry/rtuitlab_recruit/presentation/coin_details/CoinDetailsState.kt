package com.pickyberry.rtuitlab_recruit.presentation.coin_details

import com.pickyberry.rtuitlab_recruit.domain.model.CoinDetails

data class CoinDetailsState(
    val historicalData: List<Pair<Float,Float>> = listOf(),
    val coinDetails: CoinDetails? = null,
    val isLoading: Boolean = false,
    val error: String = "",
    val currency: String = "USD",
    val isFavorite: Boolean = false,
    val permissionGranted: Boolean = false
)
