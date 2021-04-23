package br.dev.tmn.cryptocurrency.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import br.dev.tmn.cryptocurrency.R
import com.facebook.drawee.view.SimpleDraweeView
import org.koin.android.ext.android.inject

class DetailFragment : Fragment() {

    private val preferences: SharedPreferences by inject()
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
        navController = requireActivity().findNavController(R.id.nav_host_fragment)
        bind(view)
        return view
    }

    private fun bind(view: View) {
        nameTextView = view.findViewById(R.id.title)
        val title = preferences.getString("cryptoTitle", "").toString()
        nameTextView.text = title

        symbolTextView = view.findViewById(R.id.symbol)
        val symbol = preferences.getString("cryptoSymbol", "").toString()
        symbolTextView.text = symbol

        descriptionTextView = view.findViewById(R.id.description)
        val description = preferences.getString("cryptoDescription", "").toString()
        descriptionTextView.text = description

        logoImage = view.findViewById(R.id.logo)
        val url = preferences.getString("cryptoLogo", "").toString()
        logoImage.setImageURI(url)

        backArrow = view.findViewById(R.id.back_arrow)
        backArrow.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}