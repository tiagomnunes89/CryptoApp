package br.dev.tmn.cryptocurrency.ui.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import br.dev.tmn.cryptocurrency.ui.viewmodels.CryptoViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject

class WorkerClass(context: Context, params: WorkerParameters) : Worker(context, params),
    KoinComponent {

    private val viewModel: CryptoViewModel by inject()

    override fun doWork(): Result {
        return try {
            viewModel.onListRequest(15)
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}