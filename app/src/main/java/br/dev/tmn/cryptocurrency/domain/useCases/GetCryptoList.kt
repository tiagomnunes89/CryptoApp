package br.dev.tmn.cryptocurrency.domain.useCases

import br.dev.tmn.cryptocurrency.domain.repositories.CryptoRepository
import org.koin.core.KoinComponent

class GetCryptoList(private val cryptoRepository: CryptoRepository) : KoinComponent {

    operator fun invoke(limit: Int) = cryptoRepository.getCryptoList(limit)
}