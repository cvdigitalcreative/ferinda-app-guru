package com.digitalcreative.appguru.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.GroupAnswer
import kotlinx.android.synthetic.main.item_group_answer.view.*

class GroupAnswerAdapter : RecyclerView.Adapter<GroupAnswerAdapter.ViewHolder>() {
    var groupAnswers = listOf<GroupAnswer>()
    var listener: OnClickListener? = null
    private var selectedChipIndex: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_group_answer, parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val groupAnswer = groupAnswers[position]
        holder.bind(groupAnswer, position)
    }

    override fun getItemCount(): Int = groupAnswers.size

    inner class ViewHolder(itemView: View, private val listener: OnClickListener?) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(groupAnswer: GroupAnswer, position: Int) {
            with(itemView) {
                chip_group_answer.tag = groupAnswer.id
                chip_group_answer.text = groupAnswer.group

                chip_group_answer.setOnClickListener {
                    listener?.onItemClicked(
                        groupAnswer.id,
                        selectedChipIndex,
                    )

                    selectedChipIndex = position
                }
            }
        }
    }

    interface OnClickListener {
        fun onItemClicked(id: String, position: Int?)
    }
}