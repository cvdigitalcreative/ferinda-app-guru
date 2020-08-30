package com.digitalcreative.appguru.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Indicator
import kotlinx.android.synthetic.main.item_report_value.view.*

class ReportValueAdapter : RecyclerView.Adapter<ReportValueAdapter.ViewHolder>() {
    var choices = listOf<Indicator.IndicatorItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_report_value, parent, false)
        )
    }

    override fun getItemCount(): Int = choices.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = choices[position]
        holder.bind(question)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(choices: Indicator.IndicatorItem) {
            with(itemView) {
                tv_value_name.text = choices.name
            }
        }
    }
}