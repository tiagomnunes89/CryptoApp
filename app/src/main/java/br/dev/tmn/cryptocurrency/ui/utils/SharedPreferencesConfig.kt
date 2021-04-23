package br.dev.tmn.cryptocurrency.ui.utils

import android.content.Context
import android.content.SharedPreferences
import br.dev.tmn.cryptocurrency.data.service.response.CryptoDetailResponse

class SharedPreferencesConfig(private val context: Context) {

    private val sharedPrefFile = "com.example.android.PREFERENCE_FILE"
    private var preferences: SharedPreferences? = null

    fun saveCurrentCryptoData(item: CryptoDetailResponse, id: Int) {
        preferences = context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val preferenceEditor = preferences?.edit()
        preferenceEditor?.putString("cryptoTitle", item.detailData[id]?.name)
        preferenceEditor?.putString("cryptoSymbol", item.detailData[id]?.symbol)
        preferenceEditor?.putString("cryptoDescription", item.detailData[id]?.description)
        preferenceEditor?.putString("cryptoLogo", item.detailData[id]?.logo)
        preferenceEditor?.apply()
    }
}