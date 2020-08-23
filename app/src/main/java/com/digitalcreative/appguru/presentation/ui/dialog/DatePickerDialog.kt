package com.digitalcreative.appguru.presentation.ui.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.*

class DatePickerDialog(private val listener: (String) -> Unit) : DialogFragment(),
    DatePickerDialog.OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(
        view: DatePicker,
        year: Int,
        monthOfYear: Int,
        dayOfMonth: Int
    ) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, monthOfYear)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        listener(format.format(calendar.time))
    }
}