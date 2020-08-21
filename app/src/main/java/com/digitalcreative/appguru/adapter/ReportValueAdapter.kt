package com.digitalcreative.appguru.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.Question
import kotlinx.android.synthetic.main.item_report_value.view.*

class ReportValueAdapter : RecyclerView.Adapter<ReportValueAdapter.ViewHolder>() {
    var questions = listOf<Question>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_report_value, parent, false)
        )
    }

    override fun getItemCount(): Int = questions.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = questions[position]
        holder.bind(question)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(question: Question) {
            with(itemView) {
                tv_value_name.text = question.name
            }
        }
    }
}