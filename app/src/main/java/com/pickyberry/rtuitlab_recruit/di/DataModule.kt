package com.pickyberry.rtuitlab_recruit.di

import android.app.Application
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.pickyberry.rtuitlab_recruit.data.database.CoinsDatabase
import com.pickyberry.rtuitlab_recruit.data.network.Api
import com.pickyberry.rtuitlab_recruit.data.network.CoinPriceDeserializer
import com.pickyberry.rtuitlab_recruit.data.network.SimpleCoinPriceDto
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

//Hilt database and network dependencies
@Module
@InstallIn(SingletonComponent::class)
class DataModule {


    @Provides
    @Singleton
    fun provideApi(): Api {

        val gson = GsonBuilder()
            .registerTypeAdapter(SimpleCoinPriceDto::class.java, CoinPriceDeserializer())
            .create()

        return Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): CoinsDatabase {
        return Room.databaseBuilder(
            app,
            CoinsDatabase::class.java,
            "cryptocoinsdb.db"
        ).fallbackToDestructiveMigration().build()
    }

}