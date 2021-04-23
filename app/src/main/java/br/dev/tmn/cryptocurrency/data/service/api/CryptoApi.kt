package br.dev.tmn.cryptocurrency.data.service.api

import br.dev.tmn.cryptocurrency.data.service.response.CryptoDetailResponse
import br.dev.tmn.cryptocurrency.data.service.response.CryptoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApi {

    @GET("cryptocurrency/listings/latest")
    fun getCryptoList(
        @Query("limit") limit: Int
    ): Call<CryptoResponse>

    @GET("cryptocurrency/info")
    fun getCryptoDetail(
        @Query("id") id: Long
    ): Call<CryptoDetailResponse>
}