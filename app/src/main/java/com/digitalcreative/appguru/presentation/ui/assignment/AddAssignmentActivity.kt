package com.digitalcreative.appguru.presentation.ui.assignment

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.utils.helper.loadingDialog
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_add_assignment.*
import kotlinx.android.synthetic.main.toolbar.*

@AndroidEntryPoint
class AddAssignmentActivity : AppCompatActivity() {

    private val viewModel by viewModels<AssignmentViewModel>()
    private val loadingDialog by loadingDialog()

    companion object {
        const val EXTRA_ID = "extra_id"
        const val RESULT_SUCCESS = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_assignment)
        setSupportActionBar(toolbar)

        val classId = intent.getStringExtra(EXTRA_ID) ?: return

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title = getString(R.string.tambah_tugas)
        }

        btn_add.setOnClickListener {
            val title = edt_assignment_title.text.toString().trim()
            val description = edt_assignment_description.text.toString().trim()
            viewModel.addAssignment(classId, title, description)
        }

        initObservers()
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