package com.pickyberry.rtuitlab_recruit.presentation.coins_list

import com.pickyberry.rtuitlab_recruit.domain.model.CoinItem
import com.pickyberry.rtuitlab_recruit.util.SortType

data class CoinsListState(
    val coins: List<CoinItem> = emptyList(),
    val query:String="",
    val isLoading:Boolean = false,
    val error: String ="",
    val displayingFavorites:Boolean = false,
    val sorted: SortType = SortType.DEFAULT
)