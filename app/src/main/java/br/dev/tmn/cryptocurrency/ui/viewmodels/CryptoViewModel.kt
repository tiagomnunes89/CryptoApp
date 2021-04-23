package br.dev.tmn.cryptocurrency.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.dev.tmn.cryptocurrency.data.service.response.CryptoDetailResponse
import br.dev.tmn.cryptocurrency.domain.entities.Crypto
import br.dev.tmn.cryptocurrency.domain.useCases.GetCryptoDetail
import br.dev.tmn.cryptocurrency.domain.useCases.GetCryptoList
import br.dev.tmn.cryptocurrency.domain.utils.Result
import br.dev.tmn.cryptocurrency.ui.utils.Data
import br.dev.tmn.cryptocurrency.ui.utils.SharedPreferencesConfig
import br.dev.tmn.cryptocurrency.ui.utils.Status
import br.dev.tmn.cryptocurrency.ui.viewmodels.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CryptoViewModel(
    private val sharedPreferencesConfig: SharedPreferencesConfig,
    val getCryptoList: GetCryptoList,
    val getCryptoDetail: GetCryptoDetail
) : BaseViewModel() {

    private var mutableMainStateList = MutableLiveData<Data<List<Crypto>>>()
    val mainStateList: LiveData<Data<List<Crypto>>>
        get() {
            return mutableMainStateList
        }

    private var mutableMainStateDetail = MutableLiveData<Data<CryptoDetailResponse>>()
    val mainStateDetail: LiveData<Data<CryptoDetailResponse>>
        get() {
            return mutableMainStateDetail
        }

    fun onListRequest(limit: Int) = launch {
        mutableMainStateList.value = Data(responseType = Status.LOADING)
        when (val result = withContext(Dispatchers.IO) { getCryptoList(limit) }) {
            is Result.Failure -> {
                mutableMainStateList.value =
                    Data(responseType = Status.ERROR, error = result.exception)
            }
            is Result.Success -> {
                mutableMainStateList.value =
                    Data(responseType = Status.SUCCESSFUL, data = result.data)
            }
        }
    }

    fun onClickToCryptoDetails(id: Long) = launch {
        mutableMainStateDetail.value = Data(responseType = Status.LOADING)
        when (val result = withContext(Dispatchers.IO) { getCryptoDetail(id) }) {
            is Result.Failure -> {
                mutableMainStateDetail.value =
                    Data(responseType = Status.ERROR, error = result.exception)
            }
            is Result.Success -> {
                mutableMainStateDetail.value =
                    Data(responseType = Status.SUCCESSFUL, data = result.data)
                result.data?.let { sharedPreferencesConfig.saveCurrentCryptoData(it, id.toInt()) }
            }
        }
    }
}