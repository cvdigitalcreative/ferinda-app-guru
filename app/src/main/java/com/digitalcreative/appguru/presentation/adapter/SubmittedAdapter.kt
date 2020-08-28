package com.digitalcreative.appguru.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Submitted
import kotlinx.android.synthetic.main.item_assignment_section.view.*

class SubmittedAdapter : RecyclerView.Adapter<SubmittedAdapter.ViewHolder>() {
    var submitteds = listOf<Submitted.AssignmentSubmitted>()
    var listener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_assignment_section, parent, false),
            listener
        )
    }

    override fun getItemCount(): Int = submitteds.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val submitted = submitteds[position]
        holder.bind(submitted)
    }

    class ViewHolder(itemView: View, private val listener: OnClickListener?) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(submitted: Submitted.AssignmentSubmitted) {
            with(itemView) {
                tv_assignment_section.text = submitted.section

                setOnClickListener {
                    listener?.onItemClicked(submitted)
                }
            }
        }
    }

    interface OnClickListener {
        fun onItemClicked(submitted: Submitted.AssignmentSubmitted)
    }
}