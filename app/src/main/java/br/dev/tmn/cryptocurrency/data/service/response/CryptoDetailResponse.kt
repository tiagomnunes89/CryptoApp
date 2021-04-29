package br.dev.tmn.cryptocurrency.data.service.response

import com.google.gson.annotations.SerializedName

class CryptoDetailResponse(
    @SerializedName("data")
    val detailData: Map<Int, DetailItem>
)

class DetailItem(
    val id: Long,
    val name: String,
    val symbol: String,
    val description: String,
    val logo: String
)
