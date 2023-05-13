package com.pickyberry.rtuitlab_recruit.data.network.details

import com.google.gson.annotations.SerializedName

data class CoinDetailsDescriptionDto (

    @SerializedName("en" ) var en : String = "",
    @SerializedName("ru" ) var ru : String = ""

)