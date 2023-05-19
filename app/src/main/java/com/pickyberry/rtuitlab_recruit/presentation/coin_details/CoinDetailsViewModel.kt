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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.zip
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
        getDetailsAndHistoricalData("USD")
    }

    //Get details and historical data simultaneously and return after all the data is received
    private fun getDetailsAndHistoricalData(currency: String) {
        state = state.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val coinId = savedStateHandle.get<String>("id") ?: return@launch

            val coinDetailsFlow = repository.getCoinDetails(coinId, false)
            val historicalDataFlow = repository.getHistoricalData(coinId, currency, false)

            coinDetailsFlow.zip(historicalDataFlow) { coinDetailsResult, historicalDataResult ->
                if (coinDetailsResult is Resource.Success && coinDetailsResult.data != null) {
                    val isFavorite = repository.isCoinFavorite(coinDetailsResult.data.id)
                    withContext(Dispatchers.Main) {
                        state = state.copy(
                            coinDetails = coinDetailsResult.data,
                            isFavorite = isFavorite ?: false,
                            error = ""
                        )
                    }
                }
                if (historicalDataResult is Resource.Success && historicalDataResult.data != null) {
                    withContext(Dispatchers.Main) {
                        state = state.copy(historicalData = historicalDataResult.data, error = "")
                    }
                }

                if (coinDetailsResult is Resource.Error) state =
                    state.copy(error = coinDetailsResult.message!!)
                if (historicalDataResult is Resource.Error) state =
                    state.copy(error = historicalDataResult.message!!)
            }.collect()

            withContext(Dispatchers.Main) {
                state = state.copy(isLoading = false)
            }

        }
    }

    fun refresh() = getDetailsAndHistoricalData(state.currency)

    fun updateCurrency() {
        state = state.copy(currency = if (state.currency == "USD") "RUB" else "USD")
        getDetailsAndHistoricalData(state.currency)
    }

    fun updateFavoriteStatus(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.toggleCoinFavoriteState(id)
            state = state.copy(isFavorite = repository.isCoinFavorite(id) ?: false)
        }
    }


}


