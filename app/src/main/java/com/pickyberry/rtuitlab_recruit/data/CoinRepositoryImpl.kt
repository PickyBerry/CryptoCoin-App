package com.pickyberry.rtuitlab_recruit.data

import android.app.Application
import android.util.Log
import com.pickyberry.rtuitlab_recruit.data.database.CoinsDatabase
import com.pickyberry.rtuitlab_recruit.data.mapper.asCoinDetails
import com.pickyberry.rtuitlab_recruit.data.network.Api
import com.pickyberry.rtuitlab_recruit.domain.CoinRepository
import com.pickyberry.rtuitlab_recruit.domain.model.CoinDetails
import com.pickyberry.rtuitlab_recruit.domain.model.CoinItem
import com.pickyberry.rtuitlab_recruit.util.InternetValidation
import com.pickyberry.rtuitlab_recruit.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoinRepositoryImpl  @Inject constructor(
    private val api: Api,
    private val db: CoinsDatabase,
    private val application: Application,
) : CoinRepository {


    override suspend fun getCoins(
        query: String,
        offlineFirst: Boolean,
    ): Flow<Resource<List<CoinItem>>> = flow {

        emit(Resource.Loading(true))


        if (offlineFirst && query.isNotBlank() || !InternetValidation.hasInternetConnection(application)) {
            val localData = db.dao.search(query)
            emit(Resource.Success(data = localData.map { it.asCoinItem() }))
            if (!InternetValidation.hasInternetConnection(application)) emit(Resource.Error("No internet connection"))
            emit(Resource.Loading(false))
            return@flow
        }
        else  {
            val response = api.getAllCoins()
            val coins = if (response.isSuccessful) {
                response.body()?.let { resultResponse ->
                    Resource.Success(resultResponse.map { it.asCoinItem() })
                }
            } else Resource.Error(response.message())
            coins?.data?.let { data ->
                db.dao.clearCoins()
                db.dao.insertCoinItems(data.map { it.asCoinItemEntity() })
                emit(Resource.Success(data = db.dao.search(query).map { it.asCoinItem() }))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getCoinDetails(
        id: String,
        offlineFirst: Boolean,
    ): Flow<Resource<CoinDetails>> = flow {
        if (InternetValidation.hasInternetConnection(application)) {
            val response = api.getCoinDetails(id)
            val coinDetails = if (response.isSuccessful)
                response.body()?.let {
                    Resource.Success(it.asCoinDetails())
                }
            else Resource.Error(response.message())
            coinDetails?.data?.let { data ->
                emit(Resource.Success(data))
                emit(Resource.Loading(false))
            }
        }
    }


    override suspend fun getHistoricalData(
        id: String,
        currency: String,
        offlineFirst: Boolean,
    ): Flow<Resource<List<List<Float>>>> = flow {
        if (InternetValidation.hasInternetConnection(application)) {
            val response = api.getHistoricalData(id, currency)
            val coinDetails = if (response.isSuccessful)
                response.body()?.let {
                    Resource.Success(it.prices)
                }
            else Resource.Error(response.message())
            coinDetails?.data?.let { data ->
                emit(Resource.Success(data))
                emit(Resource.Loading(false))
            }
        }
    }

}