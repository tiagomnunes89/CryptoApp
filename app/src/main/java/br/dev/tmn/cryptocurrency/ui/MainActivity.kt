package br.dev.tmn.cryptocurrency.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.dev.tmn.cryptocurrency.R
import com.airbnb.lottie.LottieAnimationView


class MainActivity : AppCompatActivity() {

    private lateinit var coinAnimationLoader: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isOnline(this)) {
            setContentView(R.layout.activity_main)
            coinAnimationLoader = findViewById(R.id.loading_coin)
        } else {
            setContentView(R.layout.activity_main_offline)
        }
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        }
        return false
    }

    fun showLoading() {
        coinAnimationLoader.visibility = View.VISIBLE
    }

    fun hideLoading() {
        coinAnimationLoader.visibility = View.GONE
    }

    fun showMessage(message: String) {
        Toast.makeText(
            this@MainActivity,
            message,
            Toast.LENGTH_LONG
        ).show()
    }
}

