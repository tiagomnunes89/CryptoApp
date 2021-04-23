package br.dev.tmn.cryptocurrency.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.dev.tmn.cryptocurrency.R
import br.dev.tmn.cryptocurrency.domain.entities.Crypto
import br.dev.tmn.cryptocurrency.ui.utils.Utils.format
import br.dev.tmn.cryptocurrency.ui.utils.Utils.updateColorValue

const val TYPE_HEADER = 0
const val TYPE_ITEM = 1

class CryptoAdapter(private val exampleList: List<Crypto>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(item: Crypto)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == TYPE_ITEM) {
            CryptoViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_home,
                    parent, false
                )
            )
        } else {
            HeaderViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.header_home,
                    parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CryptoViewHolder) {
            val currentItem = exampleList[position - 1]
            holder.name.text = currentItem.name
            holder.price.text = currentItem.price.format()
            holder.price1h.text = currentItem.percentChange1H.format()
            updateColorValue(
                currentItem.percentChange1H.toFloat(),
                holder.itemView.context,
                holder.price1h
            )
            holder.price24h.text = currentItem.percentChange24H.format()
            updateColorValue(
                currentItem.percentChange24H.toFloat(),
                holder.itemView.context,
                holder.price24h
            )
            holder.price7D.text = currentItem.percentChange7D.format()
            updateColorValue(
                currentItem.percentChange7D.toFloat(),
                holder.itemView.context,
                holder.price7D
            )
            holder.itemView.animation =
                loadAnimation(holder.itemView.context, R.anim.fade_scale_animation)

            holder.itemView.setOnClickListener {
                onItemClickListener.onItemClick(exampleList[position - 1])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isPositionHeader(position)) TYPE_HEADER else TYPE_ITEM
    }

    private fun isPositionHeader(position: Int): Boolean {
        return position == 0
    }

    override fun getItemCount() = exampleList.size + 1

    class CryptoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val price: TextView = itemView.findViewById(R.id.price)
        val price1h: TextView = itemView.findViewById(R.id.price1h)
        val price24h: TextView = itemView.findViewById(R.id.price24h)
        val price7D: TextView = itemView.findViewById(R.id.price7D)

    }

    class HeaderViewHolder(headerView: View) : RecyclerView.ViewHolder(headerView)
}