package br.dev.tmn.cryptocurrency

import android.app.Application
import br.dev.tmn.cryptocurrency.di.repositoriesModule
import br.dev.tmn.cryptocurrency.di.sharedPreferences
import br.dev.tmn.cryptocurrency.di.useCasesModule
import br.dev.tmn.cryptocurrency.di.viewModelModule
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CryptoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Fresco.initialize(this)

        startKoin {
            androidContext(this@CryptoApplication)
            modules(listOf(repositoriesModule, viewModelModule, useCasesModule, sharedPreferences))
        }
    }
}