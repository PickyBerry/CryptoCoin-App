package com.pickyberry.rtuitlab_recruit.domain.model

import java.util.*
import kotlin.Comparator


data class CoinItem(
    val id:String,
    val name:String,
    val symbol:String,
    val image:String,
    val currentPrice: Float,
    val priceChangePercentage: Float,
    val marketCapChangePercentage: Float,
    val isFavorite:Boolean
){

    companion object {
        fun sortNamesAscending() = Comparator<CoinItem> { o1, o2 -> o1!!.name.compareTo(o2!!.name) }
        fun sortNamesDescending() = Comparator<CoinItem> { o1, o2 -> o2!!.name.compareTo(o1!!.name) }
        fun sortPricesAscending() = Comparator<CoinItem> { o1, o2 -> o1!!.currentPrice.compareTo(o2!!.currentPrice) }
        fun sortPricesDescending() = Comparator<CoinItem> { o1, o2 -> o2!!.currentPrice.compareTo(o1!!.currentPrice) }
        fun sortPriceChangeAscending() = Comparator<CoinItem> { o1, o2 -> o1!!.priceChangePercentage.compareTo(o2!!.priceChangePercentage) }
        fun sortPriceChangeDescending() = Comparator<CoinItem> { o1, o2 -> o2!!.priceChangePercentage.compareTo(o1!!.priceChangePercentage) }
        fun sortMarketCapChangeAscending() = Comparator<CoinItem> { o1, o2 -> o1!!.marketCapChangePercentage.compareTo(o2!!.marketCapChangePercentage) }
        fun sortMarketCapChangeDescending() = Comparator<CoinItem> { o1, o2 -> o2!!.marketCapChangePercentage.compareTo(o1!!.marketCapChangePercentage) }
    }

}
