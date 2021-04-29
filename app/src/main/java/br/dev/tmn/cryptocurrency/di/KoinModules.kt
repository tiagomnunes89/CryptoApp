package br.dev.tmn.cryptocurrency.di

import br.dev.tmn.cryptocurrency.data.repositories.CryptoRepositoryImpl
import br.dev.tmn.cryptocurrency.data.service.CryptoService
import br.dev.tmn.cryptocurrency.domain.repositories.CryptoRepository
import br.dev.tmn.cryptocurrency.domain.useCases.GetCryptoDetail
import br.dev.tmn.cryptocurrency.domain.useCases.GetCryptoList
import br.dev.tmn.cryptocurrency.ui.viewmodels.CryptoViewModel
import org.koin.dsl.module

val repositoriesModule = module {
    single { CryptoService() }
    single<CryptoRepository> { CryptoRepositoryImpl(get()) }
}

val viewModelModule = module {
    single { CryptoViewModel(get(), get()) }
}

val useCasesModule = module {
    single { GetCryptoList(get()) }
    single { GetCryptoDetail(get()) }
}