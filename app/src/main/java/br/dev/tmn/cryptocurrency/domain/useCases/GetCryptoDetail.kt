package br.dev.tmn.cryptocurrency.domain.useCases

import br.dev.tmn.cryptocurrency.domain.repositories.CryptoRepository
import org.koin.core.KoinComponent

class GetCryptoDetail(private val cryptoRepository: CryptoRepository) : KoinComponent {

    operator fun invoke(id: Long) = cryptoRepository.getCryptoDetail(id)
}