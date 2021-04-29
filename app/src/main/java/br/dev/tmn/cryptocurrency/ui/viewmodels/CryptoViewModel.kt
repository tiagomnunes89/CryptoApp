package br.dev.tmn.cryptocurrency.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.dev.tmn.cryptocurrency.data.service.response.DetailItem
import br.dev.tmn.cryptocurrency.domain.entities.Crypto
import br.dev.tmn.cryptocurrency.domain.useCases.GetCryptoDetail
import br.dev.tmn.cryptocurrency.domain.useCases.GetCryptoList
import br.dev.tmn.cryptocurrency.domain.utils.Result
import br.dev.tmn.cryptocurrency.ui.utils.Data
import br.dev.tmn.cryptocurrency.ui.utils.Status
import br.dev.tmn.cryptocurrency.ui.viewmodels.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CryptoViewModel(
    val getCryptoList: GetCryptoList,
    val getCryptoDetail: GetCryptoDetail
) : BaseViewModel() {

    private var mutableMainStateList = MutableLiveData<Data<List<Crypto>>>()
    val mainStateList: LiveData<Data<List<Crypto>>>
        get() {
            return mutableMainStateList
        }

    private var mutableMainStateDetail = MutableLiveData<Data<DetailItem>>()
    val mainStateDetail: LiveData<Data<DetailItem>>
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
                    Data(
                        responseType = Status.SUCCESSFUL,
                        data = result.data?.detailData?.get(id.toInt())
                    )
            }
        }
    }
}