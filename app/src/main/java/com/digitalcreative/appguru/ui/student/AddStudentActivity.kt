package com.digitalcreative.appguru.ui.student

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.adapter.CustomDropdownAdapter
import com.digitalcreative.appguru.data.Gender
import com.digitalcreative.appguru.data.Religion
import com.digitalcreative.appguru.utils.Constants.TYPE_DROPDOWN_GENDER
import com.digitalcreative.appguru.utils.Constants.TYPE_DROPDOWN_RELIGION
import kotlinx.android.synthetic.main.activity_add_student.*
import kotlinx.android.synthetic.main.toolbar.*
import java.text.SimpleDateFormat
import java.util.*

class AddStudentActivity : AppCompatActivity() {

    private lateinit var datePicker: DatePickerDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title = "Tambah Murid"
        }

        edt_birthdate.setOnClickListener {
            if (!datePicker.isAdded) {
                datePicker.show(supportFragmentManager, null)
            }
        }

        datePicker = DatePickerDialogFragment {
            edt_birthdate.setText(it)
        }

        setupDropdown()
    }

    private fun setupDropdown() {
        val listGender = listOf(
            Gender(
                1,
                "Laki-Laki"
            ),
            Gender(
                2,
                "Perempuan"
            )
        )
        val adapterGender =
            CustomDropdownAdapter(
                this,
                R.layout.dropdown_menu_popup_item,
                listGender,
                TYPE_DROPDOWN_GENDER
            )
        dropdown_gender.setAdapter(adapterGender)

        val listReligion = listOf(
            Religion(
                1,
                "Islam"
            ),
            Religion(
                2,
                "Kristen"
            ),
            Religion(
                3,
                "Katolik"
            ),
            Religion(
                4,
                "Hindu"
            ),
            Religion(
                5,
                "Buddha"
            ),
            Religion(
                6,
                "Konghucu"
            )
        )
        val adapterReligion =
            CustomDropdownAdapter(
                this,
                R.layout.dropdown_menu_popup_item,
                listReligion,
                TYPE_DROPDOWN_RELIGION
            )
        dropdown_religion.setAdapter(adapterReligion)
    }

    class DatePickerDialogFragment(private val listener: (String) -> Unit) : DialogFragment(),
        DatePickerDialog.OnDateSetListener {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val calendar = Calendar.getInstance()
            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val day = calendar[Calendar.DAY_OF_MONTH]
            return DatePickerDialog(context!!, this, year, month, day)
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

        companion object {
            const val FLAG_START_DATE = 0
            const val FLAG_END_DATE = 1
        }
    }
}