package com.digitalcreative.appguru.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Assignment
import kotlinx.android.synthetic.main.item_question.view.*

class QuestionAdapter : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {
    var questions = listOf<Assignment.Section.Question>()
    var listener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_question, parent, false),
            listener
        )
    }

    override fun getItemCount(): Int = questions.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = questions[position]
        holder.bind(question)
    }

    class ViewHolder(itemView: View, private val listener: OnClickListener?) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(question: Assignment.Section.Question) {
            with(itemView) {
                tv_question.text = question.question

                setOnClickListener {
                    listener?.onItemClicked(question)
                }
            }
        }
    }

    interface OnClickListener {
        fun onItemClicked(question: Assignment.Section.Question)
    }
}