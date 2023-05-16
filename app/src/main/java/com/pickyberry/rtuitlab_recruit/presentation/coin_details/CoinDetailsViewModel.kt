package com.pickyberry.rtuitlab_recruit.presentation.coin_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pickyberry.rtuitlab_recruit.domain.CoinRepository
import com.pickyberry.rtuitlab_recruit.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CoinDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: CoinRepository,
) : ViewModel() {

    var state by mutableStateOf(CoinDetailsState())

    init {
        getDetailsAndHistoricalData()
    }

    private fun getDetailsAndHistoricalData() {
        state = state.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val coinId = savedStateHandle.get<String>("id") ?: return@launch


            repository
                .getCoinDetails(coinId, false)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let {
                                val isFavorite = repository.isCoinFavorite(it.id)
                               withContext(Dispatchers.Main){ state = state.copy(coinDetails = it, isFavorite = isFavorite ?: false)}
                            }
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            withContext(Dispatchers.Main){ state = state.copy(isLoading = result.isLoading)}
                        }
                    }
                }


            repository
                .getHistoricalData(coinId, state.currency, false)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let {
                                withContext(Dispatchers.Main){  state = state.copy(historicalData = it) }
                            }
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            withContext(Dispatchers.Main){    state = state.copy(isLoading = result.isLoading) }
                        }
                    }
                }

        }
    }

    fun updateCurrency() {
        state = state.copy(currency = if (state.currency == "USD") "RUB" else "USD")
    }

    fun updateFavoriteStatus(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.toggleCoinFavoriteState(id)
            state = state.copy(isFavorite = repository.isCoinFavorite(id) ?: false)
        }
    }
}