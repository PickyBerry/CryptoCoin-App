package com.pickyberry.rtuitlab_recruit.domain

import com.pickyberry.rtuitlab_recruit.domain.model.CoinItem
import com.pickyberry.rtuitlab_recruit.util.Resource
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    suspend fun getCoins(
        query: String,
        offlineFirst:Boolean
    ): Flow<Resource<List<CoinItem>>>
}