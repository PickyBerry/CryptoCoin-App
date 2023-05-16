package com.pickyberry.rtuitlab_recruit.presentation.coins_list

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pickyberry.rtuitlab_recruit.domain.CoinRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.zxing.integration.android.IntentIntegrator
import com.pickyberry.rtuitlab_recruit.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoinsListViewModel @Inject constructor(
    private val repository:CoinRepository
    ): ViewModel() {

    var state by mutableStateOf(CoinsListState())

    private var searchJob: Job? = null

    init {
        getCoinsList()
    }

    fun refresh() = getCoinsList(offlineFirst = false,query=state.query.lowercase())

    fun search(query: String) {
        state = state.copy(query = query)
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            if (state.displayingFavorites)
                getFavoritesList()
            else getCoinsList()
        }
    }

    fun toggleFavorites(toggle: Boolean){
    //    if (toggle!=state.displayingFavorites) {
            state = state.copy(displayingFavorites = toggle)
            if (state.displayingFavorites)
                getFavoritesList()
            else getCoinsList()
    //    }
    }


    private fun getCoinsList(
        query: String = state.query.lowercase(),
        offlineFirst: Boolean = true
    ) {
        viewModelScope.launch {
            repository
                .getCoins(query, offlineFirst)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let {
                                state = state.copy(coins = it,error="")
                            }
                        }
                        is Resource.Error -> {
                            state=state.copy(error = result.message!!)
                        }
                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                    }
                }
        }
    }


    private fun getFavoritesList(
        query: String = state.query.lowercase()
    ) {
        viewModelScope.launch {
            repository
                .getFavorites(query)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let {
                                state = state.copy(coins = it,error="")
                            }
                        }
                        is Resource.Error -> {
                            state=state.copy(error = result.message!!)
                        }
                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                    }
                }
        }
    }

}