package com.pickyberry.rtuitlab_recruit.presentation.coin_details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pickyberry.rtuitlab_recruit.domain.CoinRepository
import com.pickyberry.rtuitlab_recruit.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailsViewModel  @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: CoinRepository
) : ViewModel() {

    var state by mutableStateOf(CoinDetailsState())
    
}