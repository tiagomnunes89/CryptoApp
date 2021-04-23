package br.dev.tmn.cryptocurrency.domain.repositories

import br.dev.tmn.cryptocurrency.data.service.response.CryptoDetailResponse
import br.dev.tmn.cryptocurrency.domain.entities.Crypto
import br.dev.tmn.cryptocurrency.domain.utils.Result

interface CryptoRepository {

    fun getCryptoList(limit: Int): Result<List<Crypto>>

    fun getCryptoDetail(id: Long): Result<CryptoDetailResponse>
}