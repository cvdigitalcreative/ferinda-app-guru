package com.digitalcreative.appguru.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Indicator
import kotlinx.android.synthetic.main.item_detail_report_indicator.view.*

class ReportIndicatorAdapter : RecyclerView.Adapter<ReportIndicatorAdapter.ViewHolder>() {
    var indicators = listOf<Indicator>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_detail_report_indicator, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val indicator = indicators[position]
        holder.bind(indicator)
    }

    override fun getItemCount(): Int = indicators.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(indicator: Indicator) {
            with(itemView) {
                tv_report_indicator.text = indicator.name

                val reportValueAdapter = ReportValueAdapter().apply {
                    choices = indicator.items
                }

                rv_detail_indicator.apply {
                    adapter = reportValueAdapter
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                }
            }
        }
    }
}