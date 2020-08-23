package com.digitalcreative.appguru.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Report
import kotlinx.android.synthetic.main.item_report.view.*

class ReportAdapter : RecyclerView.Adapter<ReportAdapter.ViewHolder>() {
    var raports = listOf<Report>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_report, parent, false)
        )
    }

    override fun getItemCount(): Int = raports.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val raport = raports[position]
        holder.bind(raport)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(report: Report) {
            with(itemView) {
                tv_raport_item.text = report.name
            }
        }
    }
}