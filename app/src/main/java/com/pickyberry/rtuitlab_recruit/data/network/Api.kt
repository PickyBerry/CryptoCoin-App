package com.pickyberry.rtuitlab_recruit.data.network


import retrofit2.Response
import retrofit2.http.GET

interface Api {

    @GET("coins/")
    suspend fun getAllCoins(): Response<List<CoinDto>>

    companion object {
        const val BASE_URL = "https://api.coinpaprika.com/v1/"
    }

}