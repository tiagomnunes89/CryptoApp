package br.dev.tmn.cryptocurrency

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import br.dev.tmn.cryptocurrency.data.service.response.CryptoDetailResponse
import br.dev.tmn.cryptocurrency.di.useCasesModule
import br.dev.tmn.cryptocurrency.domain.entities.Crypto
import br.dev.tmn.cryptocurrency.domain.useCases.GetCryptoDetail
import br.dev.tmn.cryptocurrency.domain.useCases.GetCryptoList
import br.dev.tmn.cryptocurrency.domain.utils.Result
import br.dev.tmn.cryptocurrency.ui.utils.Data
import br.dev.tmn.cryptocurrency.ui.utils.Status
import br.dev.tmn.cryptocurrency.ui.viewmodels.CryptoViewModel
import com.google.common.truth.Truth
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

private const val VALID_LIMIT = 15
private const val INVALID_LIMIT = -1
private const val INVALID_ID = -1L
private const val VALID_ID = 1L

class CryptoViewModelTest : AutoCloseKoinTest() {

    @ObsoleteCoroutinesApi
    private var mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var cryptoViewModel: CryptoViewModel

    @Mock
    lateinit var cryptoListValidResult: Result.Success<List<Crypto>>

    @Mock
    lateinit var cryptoInvalidResult: Result.Failure

    @Mock
    lateinit var detailValidResult: Result.Success<CryptoDetailResponse>

    @Mock
    lateinit var cryptoList: List<Crypto>

    @Mock
    lateinit var cryptoDetailResponse: CryptoDetailResponse

    @Mock
    lateinit var exception: Exception

    private val getCryptoList: GetCryptoList by inject()
    private val getCryptoDetail: GetCryptoDetail by inject()


    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        startKoin {
            androidContext(CryptoApplication())
            modules(
                listOf(
                    useCasesModule
                )
            )
        }

        declareMock<GetCryptoList>()
        declareMock<GetCryptoDetail>()
        MockitoAnnotations.initMocks(this)
        cryptoViewModel = CryptoViewModel(
            getCryptoList,
            getCryptoDetail
        )
    }

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @After
    fun after() {
        stopKoin()
        mainThreadSurrogate.close()
        Dispatchers.resetMain()
    }

    @Test
    fun `when getCryptoList is successful`() {
        Mockito.`when`(getCryptoList.invoke(VALID_LIMIT)).thenReturn(cryptoListValidResult)
        Mockito.`when`(cryptoListValidResult.data).thenReturn(cryptoList)
        runBlocking {
            cryptoViewModel.onListRequest(VALID_LIMIT).join()
        }
        val liveDataUnderTest = cryptoViewModel.mainStateList.testObserver()
        Truth.assertThat(liveDataUnderTest.observedValues[0])
            .isEqualTo(Data(responseType = Status.SUCCESSFUL, data = cryptoListValidResult.data))
    }

    @Test
    fun `when getCryptoList is failure`() {
        Mockito.`when`(getCryptoList.invoke(INVALID_LIMIT)).thenReturn(cryptoInvalidResult)
        Mockito.`when`(cryptoInvalidResult.exception).thenReturn(exception)
        runBlocking {
            cryptoViewModel.onListRequest(INVALID_LIMIT).join()
        }
        val liveDataUnderTest = cryptoViewModel.mainStateList.testObserver()
        Truth.assertThat(liveDataUnderTest.observedValues[0])
            .isEqualTo(
                Data(
                    responseType = Status.ERROR,
                    data = null,
                    error = cryptoInvalidResult.exception
                )
            )
    }

    @Test
    fun `when getCryptoDetail is successful`() {
        Mockito.`when`(getCryptoDetail.invoke(VALID_ID)).thenReturn(detailValidResult)
        Mockito.`when`(detailValidResult.data).thenReturn(cryptoDetailResponse)
        runBlocking {
            cryptoViewModel.onClickToCryptoDetails(VALID_ID).join()
        }
        val liveDataUnderTest = cryptoViewModel.mainStateDetail.testObserver()
        Truth.assertThat(liveDataUnderTest.observedValues[0])
            .isEqualTo(
                Data(
                    responseType = Status.SUCCESSFUL,
                    data = detailValidResult.data?.detailData?.get(VALID_ID.toInt())
                )
            )
    }

    @Test
    fun `when getCryptoDetail is failure`() {
        Mockito.`when`(getCryptoDetail.invoke(INVALID_ID))
            .thenReturn(cryptoInvalidResult)
        Mockito.`when`(cryptoInvalidResult.exception).thenReturn(exception)
        runBlocking {
            cryptoViewModel.onClickToCryptoDetails(INVALID_ID).join()
        }
        val liveDataUnderTest = cryptoViewModel.mainStateDetail.testObserver()
        Truth.assertThat(liveDataUnderTest.observedValues[0])
            .isEqualTo(
                Data(
                    responseType = Status.ERROR,
                    data = null,
                    error = cryptoInvalidResult.exception
                )
            )
    }

    class TestObserver<T> : Observer<T> {
        val observedValues = mutableListOf<T?>()
        override fun onChanged(value: T?) {
            observedValues.add(value)
        }
    }

    private fun <T> LiveData<T>.testObserver() = TestObserver<T>().also {
        observeForever(it)
    }
}