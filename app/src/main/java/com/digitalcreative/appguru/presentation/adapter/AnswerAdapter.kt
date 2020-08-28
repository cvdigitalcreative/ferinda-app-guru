package com.digitalcreative.appguru.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Submitted
import kotlinx.android.synthetic.main.item_answer.view.*

class AnswerAdapter : RecyclerView.Adapter<AnswerAdapter.ViewHolder>() {
    var answers = listOf<Submitted.AssignmentSubmitted.Question>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_answer, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val answer = answers[position]
        holder.bind(answer)
    }

    override fun getItemCount(): Int = answers.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(answer: Submitted.AssignmentSubmitted.Question) {
            with(itemView) {
                tv_answer_title.text = answer.question
                tv_answer_value.text = answer.choices?.choice
            }
        }
    }
}