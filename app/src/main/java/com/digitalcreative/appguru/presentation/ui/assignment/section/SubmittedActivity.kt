package com.digitalcreative.appguru.presentation.ui.assignment.section

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Student
import com.digitalcreative.appguru.data.model.Submitted
import com.digitalcreative.appguru.presentation.adapter.SubmittedAdapter
import com.digitalcreative.appguru.presentation.ui.answer.AnswerActivity
import com.digitalcreative.appguru.utils.helper.loadingDialog
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_submitted.*
import kotlinx.android.synthetic.main.toolbar.*

@AndroidEntryPoint
class SubmittedActivity : AppCompatActivity(), SubmittedAdapter.OnClickListener {

    private val viewModel by viewModels<SubmittedViewModel>()
    private val loadingDialog by loadingDialog()
    private val submittedAdapter = SubmittedAdapter()

    private lateinit var classId: String
    private lateinit var assignmentId: String
    private lateinit var student: Student

    companion object {
        const val EXTRA_CLASS_ID = "extra_class_id"
        const val EXTRA_ASSIGNMENT_ID = "extra_assignment_id"
        const val EXTRA_STUDENT = "extra_student"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submitted)
        setSupportActionBar(toolbar)

        classId = intent.getStringExtra(EXTRA_CLASS_ID) ?: return
        assignmentId = intent.getStringExtra(EXTRA_ASSIGNMENT_ID) ?: return
        student = intent.getParcelableExtra(EXTRA_STUDENT) ?: return

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title = student.name
        }

        submittedAdapter.listener = this

        rv_submitted.apply {
            adapter = submittedAdapter
            layoutManager = LinearLayoutManager(this@SubmittedActivity)
            setHasFixedSize(true)
        }

        initObservers()
        viewModel.getAssignmentSectionSubmitted(classId, student.id, assignmentId)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onItemClicked(submitted: Submitted.AssignmentSubmitted) {
        val intent = Intent(this, AnswerActivity::class.java).apply {
            putExtra(AnswerActivity.EXTRA_DATA, submitted)
            putExtra(AnswerActivity.EXTRA_STUDENT, student)
        }
        startActivity(intent)
    }

    private fun initObservers() {
        viewModel.loading.observe(this, Observer(this::showLoading))
        viewModel.submitted.observe(this, Observer(this::showSubmitted))
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

    private fun showSubmitted(submitted: Submitted) {
        tv_points.text = submitted.points

        submittedAdapter.apply {
            this.submitteds = submitted.data
            notifyDataSetChanged()
        }
    }

    private fun showSuccessMessage(message: String) {
        Toasty.success(this, message, Toasty.LENGTH_LONG, true).show()
    }

    private fun showErrorMessage(message: String) {
        Toasty.error(this, message, Toasty.LENGTH_LONG, true).show()
    }
}