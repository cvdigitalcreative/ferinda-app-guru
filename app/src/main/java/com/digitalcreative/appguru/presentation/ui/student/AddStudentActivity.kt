package com.digitalcreative.appguru.presentation.ui.student

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Classroom
import com.digitalcreative.appguru.data.model.Gender
import com.digitalcreative.appguru.data.model.Religion
import com.digitalcreative.appguru.presentation.adapter.CustomDropdownAdapter
import com.digitalcreative.appguru.presentation.ui.dialog.DatePickerDialog
import com.digitalcreative.appguru.utils.helper.Constants.TYPE_DROPDOWN_CLASS
import com.digitalcreative.appguru.utils.helper.Constants.TYPE_DROPDOWN_GENDER
import com.digitalcreative.appguru.utils.helper.Constants.TYPE_DROPDOWN_RELIGION
import com.digitalcreative.appguru.utils.helper.loadingDialog
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_add_student.*
import kotlinx.android.synthetic.main.toolbar.*

@AndroidEntryPoint
class AddStudentActivity : AppCompatActivity() {

    private val viewModel by viewModels<StudentViewModel>()
    private val loadingDialog by loadingDialog()

    private lateinit var datePicker: DatePickerDialog
    private lateinit var genderId: String
    private lateinit var religionId: String
    private lateinit var classId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title = getString(R.string.tambah_murid)
        }

        edt_birthdate.setOnClickListener {
            if (!datePicker.isAdded) {
                datePicker.show(supportFragmentManager, null)
            }
        }

        dropdown_gender.setOnItemClickListener { parent, _, position, _ ->
            genderId = (parent.adapter.getItem(position) as Gender).id
        }

        dropdown_religion.setOnItemClickListener { parent, _, position, _ ->
            religionId = (parent.adapter.getItem(position) as Religion).id
        }

        dropdown_class.setOnItemClickListener { parent, _, position, _ ->
            classId = (parent.adapter.getItem(position) as Classroom).id
        }

        datePicker = DatePickerDialog {
            edt_birthdate.setText(it)
        }

        initObservers()
    }

    private fun initObservers() {
        viewModel.loading.observe(this, Observer(this::showLoading))
        viewModel.gender.observe(this, Observer(this::showGender))
        viewModel.religion.observe(this, Observer(this::showReligion))
        viewModel.classroom.observe(this, Observer(this::showClassroom))
        viewModel.successMessage.observe(this, Observer(this::showSuccessMessage))
        viewModel.errorMessage.observe(this, Observer(this::showErrorMessage))
    }

    private fun showLoading(isShow: Boolean) {
        if (isShow) {
            if (!loadingDialog.isAdded) {
                loadingDialog.showDialog()
            }
        } else {
            if (loadingDialog.isAdded) {
                loadingDialog.closeDialog()
            }
        }
    }

    private fun showGender(genders: List<Gender>) {
        val adapterGender =
            CustomDropdownAdapter(
                this,
                R.layout.dropdown_menu_popup_item,
                genders,
                TYPE_DROPDOWN_GENDER
            )
        dropdown_gender.setAdapter(adapterGender)
    }

    private fun showReligion(religions: List<Religion>) {
        val adapterReligion =
            CustomDropdownAdapter(
                this,
                R.layout.dropdown_menu_popup_item,
                religions,
                TYPE_DROPDOWN_RELIGION
            )
        dropdown_religion.setAdapter(adapterReligion)
    }

    private fun showClassroom(classrooms: List<Classroom>) {
        val adapterClassroom =
            CustomDropdownAdapter(
                this,
                R.layout.dropdown_menu_popup_item,
                classrooms,
                TYPE_DROPDOWN_CLASS
            )
        dropdown_class.setAdapter(adapterClassroom)
    }

    private fun showSuccessMessage(message: String) {
        Toasty.success(this, message, Toasty.LENGTH_LONG, true).show()
    }

    private fun showErrorMessage(message: String) {
        Toasty.error(this, message, Toasty.LENGTH_LONG, true).show()
    }
}