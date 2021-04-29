package br.dev.tmn.cryptocurrency.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import br.dev.tmn.cryptocurrency.R
import br.dev.tmn.cryptocurrency.data.service.response.DetailItem
import br.dev.tmn.cryptocurrency.ui.MainActivity
import br.dev.tmn.cryptocurrency.ui.utils.Data
import br.dev.tmn.cryptocurrency.ui.utils.Status
import br.dev.tmn.cryptocurrency.ui.viewmodels.CryptoViewModel
import com.facebook.drawee.view.SimpleDraweeView
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    private val viewModel by viewModel<CryptoViewModel>()
    private lateinit var nameTextView: TextView
    private lateinit var logoImage: SimpleDraweeView
    private lateinit var descriptionTextView: TextView
    private lateinit var symbolTextView: TextView
    private lateinit var backArrow: ImageView
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        viewModel.mainStateDetail.observe(::getLifecycle, ::updateDetailUI)
        navController = requireActivity().findNavController(R.id.nav_host_fragment)
        bind(view)
        return view
    }

    private fun bind(view: View) {
        nameTextView = view.findViewById(R.id.title)

        symbolTextView = view.findViewById(R.id.symbol)

        descriptionTextView = view.findViewById(R.id.description)

        logoImage = view.findViewById(R.id.logo)

        backArrow = view.findViewById(R.id.back_arrow)

        backArrow.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun updateDetailUI(detailItem: Data<DetailItem>) {
        when (detailItem.responseType) {
            Status.ERROR -> {
                (activity as MainActivity).hideLoading()
                detailItem.error?.message?.let { (activity as MainActivity).showMessage(it) }
                Log.d(RESULT, "ERROR")
            }
            Status.LOADING -> {
                (activity as MainActivity).showLoading()
            }
            Status.SUCCESSFUL -> {
                detailItem.data?.let {
                    nameTextView.text = it.name
                    symbolTextView.text = it.symbol
                    descriptionTextView.text = it.description
                    logoImage.setImageURI(it.logo)
                }
                (activity as MainActivity).hideLoading()
                Log.d(RESULT, "SUCCESS")
            }
        }
    }
}