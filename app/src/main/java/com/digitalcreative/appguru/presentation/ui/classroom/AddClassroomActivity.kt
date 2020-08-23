package com.digitalcreative.appguru.presentation.ui.classroom

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
        const val RESULT_SUCCESS = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_classroom)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title = getString(R.string.tambah_kelas)
        }

        initObservers()

        btn_add.setOnClickListener {
            val className = edt_class_name.text.toString().trim()
            viewModel.addClassroom(className)
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
        setResult(RESULT_SUCCESS)
        finish()
    }

    private fun showErrorMessage(message: String) {
        Toasty.error(this, message, Toasty.LENGTH_LONG, true).show()
    }
}