package com.digitalcreative.appguru.presentation.ui.assignment

import android.content.Intent
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
        const val EXTRA_TYPE = "extra_type"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_DESCRIPTION = "extra_description"
        const val EXTRA_RESULT = "extra_result"
        const val TYPE_EDIT = 1
        const val EXTRA_ID = "extra_id"
        const val EXTRA_ASSIGNMENT_ID = "extra_assignment_id"
        const val RESULT_SUCCESS = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_assignment)
        setSupportActionBar(toolbar)

        val classId = intent.getStringExtra(EXTRA_ID) ?: return
        val type = intent.getIntExtra(EXTRA_TYPE, 0)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title =
                if (type == TYPE_EDIT) getString(R.string.edit_tugas) else getString(R.string.tambah_tugas)
        }

        if (type == TYPE_EDIT) {
            intent.getStringExtra(EXTRA_TITLE)?.let { edt_assignment_title.setText(it) }
            intent.getStringExtra(EXTRA_DESCRIPTION)?.let { edt_assignment_description.setText(it) }

            btn_add.text = getString(R.string.edit)
        }

        btn_add.setOnClickListener {
            val title = edt_assignment_title.text.toString().trim()
            val description = edt_assignment_description.text.toString().trim()

            if (type == TYPE_EDIT) {
                val assignmentId = intent.getStringExtra(EXTRA_ASSIGNMENT_ID) ?: ""
                viewModel.editAssignment(classId, assignmentId, title, description)
            } else {
                viewModel.addAssignment(classId, title, description)
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
            putExtra(EXTRA_RESULT, edt_assignment_title.text.toString().trim())
        })
        finish()
    }

    private fun showErrorMessage(message: String) {
        Toasty.error(this, message, Toasty.LENGTH_LONG, true).show()
    }
}