package com.digitalcreative.appguru.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Assignment
import kotlinx.android.synthetic.main.item_assignment.view.*

class AssignmentAdapter : RecyclerView.Adapter<AssignmentAdapter.ViewHolder>() {
    var assignments = listOf<Assignment>()
    var listener: ClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_assignment, parent, false),
            listener
        )
    }

    override fun getItemCount(): Int = assignments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val assignment = assignments[position]
        holder.bind(assignment)
    }

    class ViewHolder(itemView: View, private val listener: ClickListener?) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(assignment: Assignment) {
            with(itemView) {
                tv_assignment_title.text = assignment.title
                tv_assignment_description.text = assignment.description

                setOnClickListener {
                    listener?.onItemClicked(assignment)
                }
            }
        }
    }

    interface ClickListener {
        fun onItemClicked(assignment: Assignment)
    }
}