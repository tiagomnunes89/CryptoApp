package br.dev.tmn.cryptocurrency.data.service.response

import com.google.gson.annotations.SerializedName

class CryptoDetailResponse(
    @SerializedName("data")
    val detailData: Map<Int, Item>
)

class Item(
    val id: Long,
    val name: String,
    val symbol: String,
    val description: String,
    val logo: String
)
