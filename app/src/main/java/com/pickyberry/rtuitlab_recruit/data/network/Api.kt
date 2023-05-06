package com.pickyberry.rtuitlab_recruit.data.network


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    //https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=false&locale=en
    @GET("coins/list/")
    suspend fun getAllCoins(): Response<List<CoinDto>>

    @GET("coins/{id}/market_chart?days=91/")
    suspend fun getHistorical(@Path("id")id:String,@Query("vs_currency")currency:String):Response<HistoricalDataDto>


    companion object {
        const val BASE_URL = "https://api.coingecko.com/api/v3/"
    }
}