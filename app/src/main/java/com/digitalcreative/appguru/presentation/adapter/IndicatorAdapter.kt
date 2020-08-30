package com.digitalcreative.appguru.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Indicator
import kotlinx.android.synthetic.main.item_report_indicator.view.*

class IndicatorAdapter : RecyclerView.Adapter<IndicatorAdapter.ViewHolder>() {
    var indicators = listOf<Indicator>()
    var listener: OnClickListener? = null

    private val listIndicator = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_report_indicator, parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val indicator = indicators[position]
        holder.bind(indicator)
    }

    override fun getItemCount(): Int = indicators.size

    inner class ViewHolder(itemView: View, private val listener: OnClickListener?) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(indicator: Indicator) {
            with(itemView) {
                tv_indicator_value.text = indicator.name

                setOnClickListener {
                    if (listIndicator.contains(indicator.id)) {
                        setStyleUnActive(context, card_view, tv_indicator_value)

                        listIndicator.remove(indicator.id)
                        listener?.onItemUnClicked(indicator.id)
                    } else {
                        setStyleActive(context, card_view, tv_indicator_value)

                        listIndicator.add(indicator.id)
                        listener?.onItemClicked(indicator.id)
                    }
                }
            }
        }

        private fun setStyleActive(context: Context, cardView: CardView, textView: TextView) {
            cardView.setCardBackgroundColor(
                context.resources.getColor(
                    R.color.colorPrimaryDark,
                    null
                )
            )

            textView.setTextColor(context.resources.getColor(android.R.color.white, null))
        }

        private fun setStyleUnActive(context: Context, cardView: CardView, textView: TextView) {
            cardView.setCardBackgroundColor(
                context.resources.getColor(
                    android.R.color.white,
                    null
                )
            )

            textView.setTextColor(context.resources.getColor(android.R.color.black, null))
        }
    }

    interface OnClickListener {
        fun onItemClicked(id: String)
        fun onItemUnClicked(id: String)
    }
}
