package com.digitalcreative.appguru.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Gender
import com.digitalcreative.appguru.data.model.Religion
import com.digitalcreative.appguru.utils.helper.Constants.TYPE_DROPDOWN_GENDER
import com.digitalcreative.appguru.utils.helper.Constants.TYPE_DROPDOWN_RELIGION


class CustomDropdownAdapter(
    context: Context,
    private val layout: Int,
    private val data: List<Any>,
    private val type: Int
) : ArrayAdapter<Any>(context, layout, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = convertView ?: inflater.inflate(layout, null)
        val tvDropdown = view.findViewById<TextView>(R.id.tv_dropdown)

        when (type) {
            TYPE_DROPDOWN_GENDER -> {
                val item = (data[position] as Gender)
                tvDropdown.text = item.name
            }
            TYPE_DROPDOWN_RELIGION -> {
                val item = (data[position] as Religion)
                tvDropdown.text = item.name
            }
        }
        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun convertResultToString(resultValue: Any?): CharSequence {
                return when (type) {
                    TYPE_DROPDOWN_GENDER -> {
                        (resultValue as Gender).name
                    }
                    TYPE_DROPDOWN_RELIGION -> {
                        (resultValue as Religion).name
                    }
                    else -> ""
                }
            }

            override fun performFiltering(constraint: CharSequence?): FilterResults =
                FilterResults()

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {}
        }
    }
}