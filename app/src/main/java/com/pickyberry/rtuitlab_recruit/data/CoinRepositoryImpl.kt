package com.pickyberry.rtuitlab_recruit.data

import android.app.Application
import android.util.Log
import com.pickyberry.rtuitlab_recruit.data.database.CoinsDatabase
import com.pickyberry.rtuitlab_recruit.data.database.entity.HistoricalDataEntity
import com.pickyberry.rtuitlab_recruit.data.mapper.asCoinDetails
import com.pickyberry.rtuitlab_recruit.data.mapper.asCoinDetailsEntity
import com.pickyberry.rtuitlab_recruit.data.mapper.asSimpleCoinPriceItem
import com.pickyberry.rtuitlab_recruit.data.network.Api
import com.pickyberry.rtuitlab_recruit.domain.CoinRepository
import com.pickyberry.rtuitlab_recruit.domain.model.CoinDetails
import com.pickyberry.rtuitlab_recruit.domain.model.CoinItem
import com.pickyberry.rtuitlab_recruit.domain.model.SimpleCoinPriceItem
import com.pickyberry.rtuitlab_recruit.util.InternetValidation
import com.pickyberry.rtuitlab_recruit.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.stream.Collectors
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
        if (localData != null) emit(Resource.Success(data = localData.map { it.asCoinItem() }))
        if (!InternetValidation.hasInternetConnection(application)) emit(Resource.Error("No internet connection"))
        emit(Resource.Loading(false))
        if (offlineFirst && query.isNotBlank() || !InternetValidation.hasInternetConnection(application)) return@flow
        else {

            val response = api.getAllCoins()
            val coins = if (response.isSuccessful) {
                response.body()?.let { resultResponse ->
                    Resource.Success(resultResponse.map { it.asCoinItem() })
                }
            } else Resource.Error(response.message())
            coins?.data?.let { networkData ->

                val networkItems = networkData.map { it.asCoinItemEntity() }
                val mergedData = if (localData != null) {

                    networkItems.map { networkItem ->
                        val localItem = localData.find { it.id == networkItem.id }
                        if (localItem != null) {
                            networkItem.copy(isFavorite = localItem.isFavorite)
                        } else {
                            networkItem.copy(isFavorite = false)
                        }
                    }

                } else networkItems

                db.coinItemDao.clearCoins()
                db.coinItemDao.insertCoinItems(mergedData)
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
        if (localData != null) emit(Resource.Success(data = localData.asCoinDetails()))
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
    ): Flow<Resource<List<Pair<Float, Float>>>> = flow {
        emit(Resource.Loading(true))
        val localData = db.historicalDataDao.getHistoricalData(id + "_$currency")
        if (localData != null) emit(Resource.Success(data = localData.prices))
        if (!InternetValidation.hasInternetConnection(application)) emit(Resource.Error("No internet connection"))
        emit(Resource.Loading(false))
        if (offlineFirst || !InternetValidation.hasInternetConnection(application))
            return@flow
        else {
            val response = api.getHistoricalData(id, currency)
            val historicalData = if (response.isSuccessful)
                response.body()?.let {
                    Resource.Success(it.prices)
                }
            else Resource.Error(response.message())
            historicalData?.data?.let { data ->

                db.historicalDataDao.insertHistoricalData(
                    HistoricalDataEntity(
                        id + "_$currency",
                        data.stream().map {
                            Pair(it[0], it[1])
                        }.collect(Collectors.toList())
                    )
                )
                val dataFromdb = db.historicalDataDao.getHistoricalData(id + "_$currency")
                emit(Resource.Success(dataFromdb.prices))
                emit(Resource.Loading(false))

            }
        }
    }

    override suspend fun toggleCoinFavoriteState(id: String) {
        db.coinItemDao.toggleCoinFavorite(id)
    }

    override suspend fun getFavorites(query: String): Flow<Resource<List<CoinItem>>> = flow{
        val localData = db.coinItemDao.searchFavorites(query)
        emit(Resource.Success(data = localData.map { it.asCoinItem() }))
    }

    override suspend fun isCoinFavorite(id: String) = db.coinItemDao.isCoinFavorite(id)
    override suspend fun getSimpleCoinPrice(
        id: String,
        currency: String,
    ): SimpleCoinPriceItem? {
        if (InternetValidation.hasInternetConnection(application)){
            val response = api.getSimplePrice(id,currency)
            if (response.isSuccessful) {
                response.body()?.let {
                    return it.asSimpleCoinPriceItem()
                }
            }
        }
        return null
    }

}