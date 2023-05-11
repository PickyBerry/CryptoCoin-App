package com.pickyberry.rtuitlab_recruit.data.network.details

import com.google.gson.annotations.SerializedName

data class CoinDetailsLinkDto(

    @SerializedName("homepage")
    var homepage: ArrayList<String> = arrayListOf(),
    @SerializedName("blockchain_site")
    var blockchainSite: ArrayList<String> = arrayListOf()

)