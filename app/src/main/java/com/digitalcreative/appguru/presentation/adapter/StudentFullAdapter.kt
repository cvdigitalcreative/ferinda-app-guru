package com.digitalcreative.appguru.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.StudentFull
import kotlinx.android.synthetic.main.item_person.view.*

class StudentFullAdapter : RecyclerView.Adapter<StudentFullAdapter.ViewHolder>() {
    var students = listOf<StudentFull>()
    var listener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_person, parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = students[position]
        holder.bind(student)
    }

    override fun getItemCount(): Int = students.size

    class ViewHolder(itemView: View, private val listener: OnClickListener?) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(student: StudentFull) {
            with(itemView) {
                tv_person_name.text = student.name
                tv_person_id.text = student.id

                Glide.with(this)
                    .load(context.getString(R.string.ui_avatar, student.name))
                    .into(img_person)

                setOnClickListener {
                    listener?.onItemClicked(student)
                }
            }
        }
    }

    interface OnClickListener {
        fun onItemClicked(student: StudentFull)
    }
}