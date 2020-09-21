package com.digitalcreative.appguru.presentation.ui.student

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Gender
import com.digitalcreative.appguru.data.model.Religion
import com.digitalcreative.appguru.data.model.StudentFull
import com.digitalcreative.appguru.presentation.adapter.CustomDropdownAdapter
import com.digitalcreative.appguru.presentation.ui.dialog.DatePickerDialog
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

    private var genderId: String = ""
    private var religionId: String = ""

    private lateinit var genders: List<Gender>
    private lateinit var religions: List<Religion>
    private lateinit var datePicker: DatePickerDialog

    companion object {
        const val EXTRA_TYPE = "extra_type"
        const val EXTRA_CLASS_ID = "extra_class_id"
        const val EXTRA_DATA = "extra_data"
        const val TYPE_EDIT = 1
        const val RESULT_SUCCESS = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
        setSupportActionBar(toolbar)

        val type = intent.getIntExtra(EXTRA_TYPE, 0)
        val classId = intent.getStringExtra(EXTRA_CLASS_ID) ?: return
        val student = intent.getParcelableExtra<StudentFull>(EXTRA_DATA)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title =
                if (type == TYPE_EDIT) getString(R.string.edit_murid) else getString(R.string.tambah_murid)
        }

        if (type == TYPE_EDIT) {
            edt_nis.isEnabled = false
            btn_register.text = getString(R.string.edit)
        }

        student?.let {
            edt_nis.setText(it.id)
            edt_email.setText(it.email)
            edt_name.setText(it.name)
            dropdown_gender.setText(it.gender)
            dropdown_religion.setText(it.religion)
            edt_birth_place.setText(it.birthplace)
            edt_birth_date.setText(it.birthday.replace("00:00:00", "").trim())
            edt_phone.setText(it.phone)
            edt_address.setText(it.address)
        }

        edt_birth_date.setOnClickListener {
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

        datePicker = DatePickerDialog {
            edt_birth_date.setText(it)
        }

        btn_register.setOnClickListener {
            if (type == TYPE_EDIT) {
                genderId =
                    genders.find { it.name == dropdown_gender.text.toString() }?.id.toString()
                religionId =
                    religions.find { it.name == dropdown_religion.text.toString() }?.id.toString()

                editStudent(classId)
            } else {
                addStudent(classId)
            }
        }

        initObservers()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun initObservers() {
        viewModel.loading.observe(this, Observer(this::showLoading))
        viewModel.gender.observe(this, Observer(this::showGender))
        viewModel.religion.observe(this, Observer(this::showReligion))
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
        this.genders = genders

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
        this.religions = religions

        val adapterReligion =
            CustomDropdownAdapter(
                this,
                R.layout.dropdown_menu_popup_item,
                religions,
                TYPE_DROPDOWN_RELIGION
            )
        dropdown_religion.setAdapter(adapterReligion)
    }

    private fun showSuccessMessage(message: String) {
        Toasty.success(this, message, Toasty.LENGTH_LONG, true).show()
        setResult(RESULT_SUCCESS)
        finish()
    }

    private fun showErrorMessage(message: String) {
        Toasty.error(this, message, Toasty.LENGTH_LONG, true).show()
    }

    private fun addStudent(classId: String) {
        val nis = edt_nis.text.toString().trim()
        val email = edt_email.text.toString().trim()
        val name = edt_name.text.toString().trim()
        val gender = genderId
        val religion = religionId
        val birthPlace = edt_birth_place.text.toString().trim()
        val birthDate = edt_birth_date.text.toString().trim()
        val phone = edt_phone.text.toString().trim()
        val address = edt_address.text.toString().trim()

        val formData = mapOf(
            "nis" to nis,
            "email" to email,
            "name" to name,
            "gender" to gender,
            "religion" to religion,
            "birthPlace" to birthPlace,
            "birthDate" to birthDate,
            "phone" to phone,
            "address" to address,
            "classroom" to classId
        )
        viewModel.addStudent(formData)
    }

    private fun editStudent(classId: String) {
        val nis = edt_nis.text.toString().trim()
        val email = edt_email.text.toString().trim()
        val name = edt_name.text.toString().trim()
        val gender = genderId
        val religion = religionId
        val birthPlace = edt_birth_place.text.toString().trim()
        val birthDate = edt_birth_date.text.toString().trim()
        val phone = edt_phone.text.toString().trim()
        val address = edt_address.text.toString().trim()

        val formData = mapOf(
            "email" to email,
            "name" to name,
            "gender" to gender,
            "religion" to religion,
            "birthPlace" to birthPlace,
            "birthDate" to birthDate,
            "phone" to phone,
            "address" to address
        )
        viewModel.editStudent(classId, nis, formData)
    }
}