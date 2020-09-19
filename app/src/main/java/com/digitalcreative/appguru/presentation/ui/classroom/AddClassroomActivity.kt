package com.digitalcreative.appguru.presentation.ui.classroom

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.utils.helper.loadingDialog
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_add_classroom.*
import kotlinx.android.synthetic.main.toolbar.*

@AndroidEntryPoint
class AddClassroomActivity : AppCompatActivity() {

    private val viewModel by viewModels<ClassroomViewModel>()
    private val loadingDialog by loadingDialog()

    companion object {
        const val EXTRA_TYPE = "extra_type"
        const val EXTRA_CLASS = "extra_class"
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_RESULT = "extra_result"
        const val TYPE_EDIT = 1
        const val RESULT_SUCCESS = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_classroom)
        setSupportActionBar(toolbar)

        val type = intent.getIntExtra(EXTRA_TYPE, 0)
        val classId = intent.getStringExtra(EXTRA_CLASS) ?: ""

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title =
                if (type == TYPE_EDIT) getString(R.string.edit_kelas) else getString(R.string.tambah_kelas)
        }

        if (type == TYPE_EDIT) {
            btn_add.text = getString(R.string.edit)
        }

        intent.getStringExtra(EXTRA_DATA)?.let {
            edt_class_name.setText(it)
        }

        initObservers()

        btn_add.setOnClickListener {
            val className = edt_class_name.text.toString().trim()
            if (type == TYPE_EDIT) {
                viewModel.editClassroom(classId, className)
            } else {
                viewModel.addClassroom(className)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun initObservers() {
        viewModel.loading.observe(this, Observer(this::showLoading))
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

    private fun showSuccessMessage(message: String) {
        Toasty.success(this, message, Toasty.LENGTH_LONG, true).show()
        setResult(RESULT_SUCCESS, Intent().apply {
            putExtra(EXTRA_RESULT, edt_class_name.text.toString().trim())
        })
        finish()
    }

    private fun showErrorMessage(message: String) {
        Toasty.error(this, message, Toasty.LENGTH_LONG, true).show()
    }
}