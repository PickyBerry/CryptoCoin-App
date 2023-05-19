package com.pickyberry.rtuitlab_recruit.data.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

//Deserualizer for price data coming for our notification (It has uncommon json format)
class CoinPriceDeserializer : JsonDeserializer<SimpleCoinPriceDto> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): SimpleCoinPriceDto {
        val jsonObject = json?.asJsonObject ?: throw JsonParseException("Invalid JSON")
        val prices = mutableMapOf<String, CoinPrice>()
        for (entry in jsonObject.entrySet()) {
            val coinId = entry.key
            val coinJson = entry.value.asJsonObject
            val coinPrice = CoinPrice(
                coinJson.get("usd")?.asDouble,
                coinJson.get("usd_24h_change")?.asDouble,
                coinJson.get("rub")?.asDouble,
                coinJson.get("rub_24h_change")?.asDouble
            )
            prices[coinId] = coinPrice
        }
        return SimpleCoinPriceDto(prices)
    }
}
