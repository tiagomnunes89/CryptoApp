package br.dev.tmn.cryptocurrency.data.repositories

import br.dev.tmn.cryptocurrency.data.service.CryptoService
import br.dev.tmn.cryptocurrency.data.service.response.CryptoDetailResponse
import br.dev.tmn.cryptocurrency.domain.entities.Crypto
import br.dev.tmn.cryptocurrency.domain.repositories.CryptoRepository
import br.dev.tmn.cryptocurrency.domain.utils.Result

class CryptoRepositoryImpl(
    private val cryptoService: CryptoService
) : CryptoRepository {

    override fun getCryptoList(limit: Int): Result<List<Crypto>> {
        return cryptoService.getCryptoList(limit)
    }

    override fun getCryptoDetail(id: Long): Result<CryptoDetailResponse> {
        return cryptoService.getCryptoDetail(id)
    }
}