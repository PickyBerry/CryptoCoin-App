package com.pickyberry.rtuitlab_recruit.domain

import com.pickyberry.rtuitlab_recruit.domain.model.CoinDetails
import com.pickyberry.rtuitlab_recruit.domain.model.CoinItem
import com.pickyberry.rtuitlab_recruit.domain.model.SimpleCoinPriceItem
import com.pickyberry.rtuitlab_recruit.util.Resource
import kotlinx.coroutines.flow.Flow

//Repository interface that our screens can depend on
interface CoinRepository {
    suspend fun getCoins(query: String, offlineFirst: Boolean, ): Flow<Resource<List<CoinItem>>>

    suspend fun getCoinDetails(id: String, offlineFirst: Boolean, ): Flow<Resource<CoinDetails>>

    suspend fun getHistoricalData(id:String, currency:String, offlineFirst: Boolean):Flow<Resource<List<Pair<Float,Float>>>>

    suspend fun toggleCoinFavoriteState(id: String)

    suspend fun getFavorites(query: String): Flow<Resource<List<CoinItem>>>

    suspend fun isCoinFavorite(id:String):Boolean?

    suspend fun getSimpleCoinPrice(id:String,currency:String): SimpleCoinPriceItem?
}