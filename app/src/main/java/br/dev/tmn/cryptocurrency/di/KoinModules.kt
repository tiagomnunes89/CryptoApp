package br.dev.tmn.cryptocurrency.di

import android.content.Context
import android.content.SharedPreferences
import br.dev.tmn.cryptocurrency.data.repositories.CryptoRepositoryImpl
import br.dev.tmn.cryptocurrency.data.service.CryptoService
import br.dev.tmn.cryptocurrency.domain.repositories.CryptoRepository
import br.dev.tmn.cryptocurrency.domain.useCases.GetCryptoDetail
import br.dev.tmn.cryptocurrency.domain.useCases.GetCryptoList
import br.dev.tmn.cryptocurrency.ui.utils.SharedPreferencesConfig
import br.dev.tmn.cryptocurrency.ui.viewmodels.CryptoViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoriesModule = module {
    single { CryptoService() }
    single<CryptoRepository> { CryptoRepositoryImpl(get()) }
}

val viewModelModule = module {
    single { CryptoViewModel(get(), get(), get()) }
}

val useCasesModule = module {
    single { GetCryptoList(get()) }
    single { GetCryptoDetail(get()) }
}

val sharedPreferences = module {

    single { SharedPreferencesConfig(androidContext()) }

    single {
        getSharedPrefs(androidContext(), "com.example.android.PREFERENCE_FILE")
    }

    single<SharedPreferences.Editor> {
        getSharedPrefs(androidContext(), "com.example.android.PREFERENCE_FILE").edit()
    }
}

fun getSharedPrefs(context: Context, fileName: String): SharedPreferences {
    return context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
}