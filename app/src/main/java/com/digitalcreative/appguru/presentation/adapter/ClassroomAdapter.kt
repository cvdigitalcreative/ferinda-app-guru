package com.digitalcreative.appguru.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Classroom
import kotlinx.android.synthetic.main.item_class.view.*

class ClassroomAdapter : RecyclerView.Adapter<ClassroomAdapter.ViewHolder>() {
    var classrooms = listOf<Classroom>()
    var listener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_class, parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val classroom = classrooms[position]
        holder.bind(classroom)
    }

    override fun getItemCount(): Int = classrooms.size

    class ViewHolder(itemView: View, private val listener: OnClickListener?) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(classroom: Classroom) {
            with(itemView) {
                tv_class_name.text = classroom.name

                setOnClickListener {
                    listener?.onItemClicked(classroom.id)
                }
            }
        }
    }

    interface OnClickListener {
        fun onItemClicked(classId: String)
    }
}