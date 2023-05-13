package com.pickyberry.rtuitlab_recruit.presentation.coins_list

import com.pickyberry.rtuitlab_recruit.domain.model.CoinItem

data class CoinsListState(
    val coins: List<CoinItem> = emptyList(),
    val query:String="",
    val isLoading:Boolean = false,
    val error: String =""
)