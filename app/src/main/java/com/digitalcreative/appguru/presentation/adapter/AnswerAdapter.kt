package com.digitalcreative.appguru.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Answer
import kotlinx.android.synthetic.main.item_question_answer.view.*

class AnswerAdapter : RecyclerView.Adapter<AnswerAdapter.ViewHolder>() {
    var answers = listOf<Answer>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_question_answer, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val answer = answers[position]
        holder.bind(answer)
    }

    override fun getItemCount(): Int = answers.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(answer: Answer) {
            with(itemView) {
                tv_choice_name.text = answer.answer
            }
        }
    }
}