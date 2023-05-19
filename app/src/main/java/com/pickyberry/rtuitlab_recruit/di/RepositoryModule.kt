package com.pickyberry.rtuitlab_recruit.di

import com.pickyberry.rtuitlab_recruit.data.CoinRepositoryImpl
import com.pickyberry.rtuitlab_recruit.domain.CoinRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//Binds function for dependency inversion - depend on Interface, not realization
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepository(
        stockRepositoryImpl: CoinRepositoryImpl
    ): CoinRepository
}