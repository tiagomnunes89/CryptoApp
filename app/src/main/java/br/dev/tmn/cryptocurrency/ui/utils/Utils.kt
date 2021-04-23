package br.dev.tmn.cryptocurrency.ui.utils

import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat
import br.dev.tmn.cryptocurrency.R
import java.text.DecimalFormat

object Utils {

    fun updateColorValue(value: Float, context: Context, textView: TextView) {
        return when {
            value < 0.0 -> {
                textView.setTextColor(ContextCompat.getColor(context, R.color.red))
            }
            value > 0.0 -> {
                textView.setTextColor(ContextCompat.getColor(context, R.color.green))
            }
            else -> {
                if (value.toString() == "-0.0") textView.text = "0.0"
                textView.setTextColor(ContextCompat.getColor(context, R.color.white))
            }
        }
    }

    fun Double.format(): String {
        val dec = DecimalFormat("#,###.##")
        return dec.format(this)
    }
}

