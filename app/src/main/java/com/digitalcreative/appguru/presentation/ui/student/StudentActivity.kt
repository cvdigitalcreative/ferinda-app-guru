package com.digitalcreative.appguru.presentation.ui.student

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.StudentFull
import com.digitalcreative.appguru.presentation.adapter.StudentFullAdapter
import com.digitalcreative.appguru.utils.helper.loadingDialog
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_student.*
import kotlinx.android.synthetic.main.toolbar.*

@AndroidEntryPoint
class StudentActivity : AppCompatActivity(), StudentFullAdapter.OnClickListener {

    private val viewModel by viewModels<StudentViewModel>()
    private val loadingDialog by loadingDialog()
    private val studentAdapter = StudentFullAdapter()

    private val activityResults =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            this::handleResultIntent
        )

    private lateinit var classId: String

    companion object {
        const val EXTRA_CLASS_ID = "extra_class_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)
        setSupportActionBar(toolbar)

        classId = intent.getStringExtra(EXTRA_CLASS_ID) ?: return

        initObservers()

        studentAdapter.listener = this

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title = getString(R.string.daftar_murid)
        }

        rv_student.apply {
            adapter = studentAdapter
            layoutManager = LinearLayoutManager(this@StudentActivity)
            setHasFixedSize(true)
        }

        fab_student.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java).apply {
                putExtra(AddStudentActivity.EXTRA_CLASS_ID, classId)
            }
            activityResults.launch(intent)
        }

        viewModel.getAllStudent(classId)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun handleResultIntent(result: ActivityResult) {
        if (result.resultCode == AddStudentActivity.RESULT_SUCCESS) {
            viewModel.getAllStudent(classId)
        }
    }

    override fun onItemClicked(student: StudentFull) {
        val intent = Intent(this, AddStudentActivity::class.java).apply {
            putExtra(AddStudentActivity.EXTRA_TYPE, AddStudentActivity.TYPE_EDIT)
            putExtra(AddStudentActivity.EXTRA_CLASS_ID, classId)
            putExtra(AddStudentActivity.EXTRA_DATA, student)
        }
        activityResults.launch(intent)
    }

    private fun initObservers() {
        viewModel.loading.observe(this, Observer(this::showLoading))
        viewModel.student.observe(this, Observer(this::showStudent))
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

    private fun showStudent(students: List<StudentFull>) {
        studentAdapter.apply {
            this.students = students
            notifyDataSetChanged()
        }
    }

    private fun showErrorMessage(message: String) {
        Toasty.error(this, message, Toasty.LENGTH_LONG, true).show()
    }
}