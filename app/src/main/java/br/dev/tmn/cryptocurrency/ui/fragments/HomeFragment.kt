package br.dev.tmn.cryptocurrency.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import br.dev.tmn.cryptocurrency.R
import br.dev.tmn.cryptocurrency.data.service.response.CryptoDetailResponse
import br.dev.tmn.cryptocurrency.domain.entities.Crypto
import br.dev.tmn.cryptocurrency.ui.adapters.CryptoAdapter
import br.dev.tmn.cryptocurrency.ui.utils.Data
import br.dev.tmn.cryptocurrency.ui.utils.Status
import br.dev.tmn.cryptocurrency.ui.viewmodels.CryptoViewModel
import br.dev.tmn.cryptocurrency.ui.workers.WorkerClass
import com.airbnb.lottie.LottieAnimationView
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

const val RESULT = "RESULT"
const val CRYPTO_LIST_WORK_NAME = "Call crypto List"
const val LIST_SIZE = 15

class HomeFragment : Fragment() {

    private val viewModel by viewModel<CryptoViewModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController
    private lateinit var coinAnimationLoader: LottieAnimationView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        viewModel.mainStateList.observe(::getLifecycle, ::updateUI)
        viewModel.mainStateDetail.observe(::getLifecycle, ::updateDetailUI)
        recyclerView = root.findViewById(R.id.recycler_list)
        navController = requireActivity().findNavController(R.id.nav_host_fragment)
        coinAnimationLoader = root.findViewById(R.id.loading_coin)
        viewModel.onListRequest(LIST_SIZE)
        periodicWorkRequest()
        return root
    }

    private fun updateUI(cryptoData: Data<List<Crypto>>) {
        when (cryptoData.responseType) {
            Status.ERROR -> {
                hideLoading()
                cryptoData.error?.message?.let { showMessage(it) }
                cryptoData.data?.let { setCryptoList(it) }
                Log.d(RESULT, "ERROR")
            }
            Status.LOADING -> {
                showLoading()
            }
            Status.SUCCESSFUL -> {
                cryptoData.data?.let { setCryptoList(it) }
                Log.d(RESULT, "SUCCESS")
                hideLoading()
            }
        }
    }

    private fun updateDetailUI(cryptoDetailResponse: Data<CryptoDetailResponse>) {
        when (cryptoDetailResponse.responseType) {
            Status.ERROR -> {
                hideLoading()
                cryptoDetailResponse.error?.message?.let { showMessage(it) }
            }
            Status.LOADING -> {
                showLoading()
            }
            Status.SUCCESSFUL -> {
                val fragment = DetailFragment()
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .add(R.id.nav_host_fragment, fragment)
                    .addToBackStack("Home")
                    .commitAllowingStateLoss()
                hideLoading()
            }
        }
    }

    private fun periodicWorkRequest() {
        val periodicWorkRequest =
            PeriodicWorkRequest.Builder(WorkerClass::class.java, 15, TimeUnit.MINUTES)
                .build()
        WorkManager.getInstance(requireActivity().applicationContext)
            .enqueueUniquePeriodicWork(CRYPTO_LIST_WORK_NAME,  ExistingPeriodicWorkPolicy.REPLACE,periodicWorkRequest)
    }

    private fun setCryptoList(cryptoList: List<Crypto>) {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        val cryptoAdapter = CryptoAdapter(cryptoList)
        cryptoAdapter.setOnItemClickListener(itemClickListener())
        recyclerView.adapter = cryptoAdapter
    }

    private fun showLoading() {
        coinAnimationLoader.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        coinAnimationLoader.visibility = View.GONE
    }

    private fun showMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    private fun callClickDetailsService(item: Crypto) {
        viewModel.onClickToCryptoDetails(item.id)
    }

    private fun itemClickListener() = object : CryptoAdapter.OnItemClickListener {
        override fun onItemClick(item: Crypto) {
            callClickDetailsService(item)
        }
    }
}