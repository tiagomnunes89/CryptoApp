package br.dev.tmn.cryptocurrency.data.service.response

import com.google.gson.annotations.SerializedName

class CryptoResponse(
    @SerializedName("data")
    val dataList: List<Datum>
)

class Datum(
    val id: Long,
    val name: String,
    val quote: Quote
)

class Quote(
    @SerializedName("USD")
    val usd: USD
)

class USD(
    val price: Double,
    @SerializedName("percent_change_1h")
    val percentChange1H: Double,
    @SerializedName("percent_change_24h")
    val percentChange24H: Double,
    @SerializedName("percent_change_7d")
    val percentChange7D: Double
)

