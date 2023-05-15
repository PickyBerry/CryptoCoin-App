package com.pickyberry.rtuitlab_recruit.data

import android.app.Application
import android.util.Log
import com.pickyberry.rtuitlab_recruit.data.database.CoinsDatabase
import com.pickyberry.rtuitlab_recruit.data.mapper.asCoinDetails
import com.pickyberry.rtuitlab_recruit.data.mapper.asCoinDetailsEntity
import com.pickyberry.rtuitlab_recruit.data.network.Api
import com.pickyberry.rtuitlab_recruit.domain.CoinRepository
import com.pickyberry.rtuitlab_recruit.domain.model.CoinDetails
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


        val localData = db.coinItemDao.search(query)
        emit(Resource.Success(data = localData.map { it.asCoinItem() }))
        if (!InternetValidation.hasInternetConnection(application)) emit(Resource.Error("No internet connection"))
        emit(Resource.Loading(false))
        if (offlineFirst && query.isNotBlank() || !InternetValidation.hasInternetConnection(
                application
            )
        )
            return@flow
        else {
            val response = api.getAllCoins()
            val coins = if (response.isSuccessful) {
                response.body()?.let { resultResponse ->
                    Resource.Success(resultResponse.map { it.asCoinItem() })
                }
            } else Resource.Error(response.message())
            coins?.data?.let { data ->
                db.coinItemDao.clearCoins()
                db.coinItemDao.insertCoinItems(data.map { it.asCoinItemEntity() })
                emit(Resource.Success(data = db.coinItemDao.search(query).map { it.asCoinItem() }))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getCoinDetails(
        id: String,
        offlineFirst: Boolean,
    ): Flow<Resource<CoinDetails>> = flow {

        emit(Resource.Loading(true))


        val localData = db.coinDetailsDao.getCoinDetails(id)
        if (localData!=null) emit(Resource.Success(data = localData.asCoinDetails()))
        if (!InternetValidation.hasInternetConnection(application)) emit(Resource.Error("No internet connection"))
        emit(Resource.Loading(false))
        if (offlineFirst || !InternetValidation.hasInternetConnection(application))
            return@flow
        else {
            val response = api.getCoinDetails(id)
            val coinDetails = if (response.isSuccessful)
                response.body()?.let {
                    Resource.Success(it.asCoinDetails())
                }
            else Resource.Error(response.message())
            coinDetails?.data?.let { data ->
                emit(Resource.Success(data))
                emit(Resource.Loading(false))
                db.coinDetailsDao.insertCoinDetails(data.asCoinDetailsEntity())
            }
        }
    }


    override suspend fun getHistoricalData(
        id: String,
        currency: String,
        offlineFirst: Boolean,
    ): Flow<Resource<List<List<Float>>>> = flow {
        emit(Resource.Loading(true))

   /*     val localData = db.historicalDataDao.getHistoricalData(id + "_$currency")
        emit(Resource.Success(data = localData.prices.map { pair -> listOf(pair.first, pair.second) }))
        if (!InternetValidation.hasInternetConnection(application)) emit(Resource.Error("No internet connection"))
        emit(Resource.Loading(false))
        if (offlineFirst || !InternetValidation.hasInternetConnection(application))
            return@flow
        else { */
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
     //   }
    }

}