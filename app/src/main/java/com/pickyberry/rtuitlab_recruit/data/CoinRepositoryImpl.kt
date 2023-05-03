package com.pickyberry.rtuitlab_recruit.data

import android.app.Application
import com.pickyberry.rtuitlab_recruit.data.database.CoinsDatabase
import com.pickyberry.rtuitlab_recruit.data.network.Api
import com.pickyberry.rtuitlab_recruit.data.network.CoinDto
import com.pickyberry.rtuitlab_recruit.domain.CoinRepository
import com.pickyberry.rtuitlab_recruit.domain.model.CoinItem
import com.pickyberry.rtuitlab_recruit.util.InternetValidation
import com.pickyberry.rtuitlab_recruit.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: Api,
    private val db: CoinsDatabase,
    private val application: Application,
) : CoinRepository {


    override suspend fun getCoins(
        query: String,
        offlineFirst: Boolean,
    ): Flow<Resource<List<CoinItem>>> = flow {

        emit(Resource.Loading(true))

        val localData = db.dao.search(query)
        emit(Resource.Success(data = localData.map { it.asCoinItem() }))
        if ((offlineFirst && localData.isNotEmpty()) || !query.isBlank()) {
            emit(Resource.Loading(false))
            return@flow
        }

        if (InternetValidation.hasInternetConnection(application)) {
            val response = api.getAllCoins()
            val coins = handleResponse(response)
            coins.data?.let { data ->
                emit(Resource.Success(data))
                db.dao.clearCoins()
                db.dao.insertCoinItems(
                    data.map { it.asCoinItemEntity() }
                )
                emit(Resource.Success(
                    data = db.dao.search("").map { it.asCoinItem() }
                ))
                emit(Resource.Loading(false))
            }
        }
    }

    private fun handleResponse(response: retrofit2.Response<List<CoinDto>>): Resource<List<CoinItem>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse.map { it.asCoinItem() })
            }
        }
        return Resource.Error(response.message())
    }

}