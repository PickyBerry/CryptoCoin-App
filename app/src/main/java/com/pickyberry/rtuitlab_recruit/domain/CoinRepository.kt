package com.pickyberry.rtuitlab_recruit.domain

import com.pickyberry.rtuitlab_recruit.data.network.HistoricalDataDto
import com.pickyberry.rtuitlab_recruit.domain.model.CoinDetails
import com.pickyberry.rtuitlab_recruit.domain.model.CoinItem
import com.pickyberry.rtuitlab_recruit.domain.model.Currencies
import com.pickyberry.rtuitlab_recruit.util.Resource
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    suspend fun getCoins(
        query: String,
        offlineFirst: Boolean,
    ): Flow<Resource<List<CoinItem>>>

    suspend fun getCoinDetails(
        id: String,
        offlineFirst: Boolean,
    ): Flow<Resource<CoinDetails>>

    suspend fun getHistoricalData(
        id:String,
        currency:String,
        offlineFirst: Boolean
    ):Flow<Resource<List<Pair<Float,Float>>>>

    suspend fun updateFavoriteStatus(id: String, isFavorite: Boolean)
}