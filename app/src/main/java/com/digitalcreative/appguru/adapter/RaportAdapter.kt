package com.digitalcreative.appguru.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.Raport
import kotlinx.android.synthetic.main.item_raport.view.*

class RaportAdapter : RecyclerView.Adapter<RaportAdapter.ViewHolder>() {
    var raports = listOf<Raport>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_raport, parent, false)
        )
    }

    override fun getItemCount(): Int = raports.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val raport = raports[position]
        holder.bind(raport)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(raport: Raport) {
            with(itemView) {
                tv_raport_item.text = raport.name
            }
        }
    }
}