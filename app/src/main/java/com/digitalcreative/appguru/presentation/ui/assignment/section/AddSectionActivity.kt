package com.digitalcreative.appguru.presentation.ui.assignment.section

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.utils.helper.loadingDialog
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_add_section.*
import kotlinx.android.synthetic.main.toolbar.*

@AndroidEntryPoint
class AddSectionActivity : AppCompatActivity() {

    private val viewModel by viewModels<SectionViewModel>()
    private val loadingDialog by loadingDialog()

    companion object {
        const val EXTRA_CLASS_ID = "extra_class_id"
        const val EXTRA_ASSIGNMENT_ID = "extra_assignment_id"
        const val EXTRA_SECTION_ID = "extra_section_id"
        const val EXTRA_TYPE = "extra_type"
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_RESULT = "extra_result"
        const val TYPE_EDIT = 1
        const val RESULT_SUCCESS = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_section)
        setSupportActionBar(toolbar)

        val classId = intent.getStringExtra(EXTRA_CLASS_ID) ?: return
        val assignmentId = intent.getStringExtra(EXTRA_ASSIGNMENT_ID) ?: return
        val sectionId = intent.getStringExtra(EXTRA_SECTION_ID) ?: return
        val type = intent.getIntExtra(EXTRA_TYPE, 0)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title =
                if (type == TYPE_EDIT) getString(R.string.edit_bagian) else getString(R.string.tambah_bagian)
        }

        initObservers()

        if (type == TYPE_EDIT) {
            intent.getStringExtra(EXTRA_DATA)?.let { edt_section.setText(it) }
            btn_add.text = getString(R.string.edit)
        }

        btn_add.setOnClickListener {
            val section = edt_section.text.toString().trim()

            if (type == TYPE_EDIT) {
                viewModel.editAssignmentSection(classId, assignmentId, sectionId, section)
            } else {
                viewModel.addAssignmentSection(classId, assignmentId, section)
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
            putExtra(EXTRA_RESULT, edt_section.text.toString().trim())
        })
        finish()
    }

    private fun showErrorMessage(message: String) {
        Toasty.error(this, message, Toasty.LENGTH_LONG, true).show()
    }
}