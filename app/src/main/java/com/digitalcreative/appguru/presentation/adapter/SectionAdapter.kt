package com.digitalcreative.appguru.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Assignment
import kotlinx.android.synthetic.main.item_assignment_section.view.*

class SectionAdapter : RecyclerView.Adapter<SectionAdapter.ViewHolder>() {
    var sections = listOf<Assignment.Section>()
    var listener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_assignment_section, parent, false),
            listener
        )
    }

    override fun getItemCount(): Int = sections.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val section = sections[position]
        holder.bind(section)
    }

    class ViewHolder(itemView: View, private val listener: OnClickListener?) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(section: Assignment.Section) {
            with(itemView) {
                tv_assignment_section.text = section.section

                setOnClickListener {
                    listener?.onItemClicked(section)
                }
            }
        }
    }

    interface OnClickListener {
        fun onItemClicked(section: Assignment.Section)
    }
}