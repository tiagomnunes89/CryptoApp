package br.dev.tmn.cryptocurrency.data.service

import br.dev.tmn.cryptocurrency.data.CryptoRequestGenerator
import br.dev.tmn.cryptocurrency.data.mapper.CryptoMapperService
import br.dev.tmn.cryptocurrency.data.service.api.CryptoApi
import br.dev.tmn.cryptocurrency.data.service.response.CryptoDetailResponse
import br.dev.tmn.cryptocurrency.domain.entities.Crypto
import br.dev.tmn.cryptocurrency.domain.utils.Result

class CryptoService {
    private val api: CryptoRequestGenerator = CryptoRequestGenerator()
    private val mapperService: CryptoMapperService = CryptoMapperService()

    fun getCryptoList(limit: Int): Result<List<Crypto>> {
        val callResponse = api.createService(CryptoApi::class.java).getCryptoList(limit)
        val response = callResponse.execute()

        if (response.isSuccessful) {
            return Result.Success(response.body()?.dataList?.map {
                mapperService.transform(it)
            })
        } else Result.Failure(Exception(response.message()))

        return Result.Failure(Exception("Bad request/response"))
    }

    fun getCryptoDetail(id: Long): Result<CryptoDetailResponse> {
        val callResponse = api.createService(CryptoApi::class.java).getCryptoDetail(id)
        val response = callResponse.execute()

        if (response.isSuccessful) {
            return Result.Success(response.body())
        } else Result.Failure(Exception(response.message()))

        return Result.Failure(Exception("Bad request/response"))
    }
}