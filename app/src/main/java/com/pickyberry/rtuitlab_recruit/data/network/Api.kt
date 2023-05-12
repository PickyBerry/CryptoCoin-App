package com.pickyberry.rtuitlab_recruit.data.network


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=false&locale=en")
    suspend fun getAllCoins(): Response<List<CoinDto>>

    @GET("coins/{id}/market_chart?days=91/")
    suspend fun getHistoricalData(@Path("id")id:String,@Query("vs_currency")currency:String):Response<HistoricalDataDto>

    @GET("coins/{id}?tickers=false&community_data=false&developer_data=false&sparkline=false/")
    suspend fun getCoinDetails(@Path("id")id:String):Response<CoinDetailsDto>

    companion object {
        const val BASE_URL = "https://api.coingecko.com/api/v3/"
    }
}