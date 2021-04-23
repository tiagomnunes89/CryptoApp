package br.dev.tmn.cryptocurrency.data

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val CRYPTO_BASE_URL = "https://pro-api.coinmarketcap.com/v1/"
private const val API_KEY = "4979b8b3-aeb4-426c-ab1f-3990805888a8"
private const val MAX_TRYOUTS = 3
private const val INIT_TRYOUT = 1

class CryptoRequestGenerator {

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .addInterceptor { chain ->
            val defaultRequest = chain.request()

            val defaultHttpUrl = defaultRequest.url

            val httpUrl = defaultHttpUrl.newBuilder()
                .build()

            val requestBuilder = defaultRequest.newBuilder()
                .url(httpUrl)
                .addHeader("X-CMC_PRO_API_KEY", API_KEY)

            chain.proceed(requestBuilder.build())
        }
        .addInterceptor { chain ->
            val request = chain.request()
            var response = chain.proceed(request)
            var tryOuts = INIT_TRYOUT

            while (!response.isSuccessful && tryOuts < MAX_TRYOUTS) {
                Log.d(
                    this@CryptoRequestGenerator.javaClass.simpleName,
                    "intercept: timeout/connection failure, " +
                            "performing automatic retry ${(tryOuts + 1)}"
                )
                tryOuts++
                response = chain.proceed(request)
            }

            response
        }

    private val builder = Retrofit.Builder()
        .baseUrl(CRYPTO_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    fun <S> createService(serviceClass: Class<S>): S {
        val retrofit = builder.client(httpClient.build()).build()
        return retrofit.create(serviceClass)
    }
}