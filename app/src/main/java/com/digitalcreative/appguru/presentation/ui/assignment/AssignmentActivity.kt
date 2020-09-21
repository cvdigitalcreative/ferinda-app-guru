package com.digitalcreative.appguru.presentation.ui.assignment

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Assignment
import com.digitalcreative.appguru.data.model.Classroom
import com.digitalcreative.appguru.presentation.adapter.AssignmentAdapter
import com.digitalcreative.appguru.presentation.ui.assignment.section.SectionActivity
import com.digitalcreative.appguru.presentation.ui.classroom.AddClassroomActivity
import com.digitalcreative.appguru.presentation.ui.report.ReportActivity
import com.digitalcreative.appguru.presentation.ui.student.StudentActivity
import com.digitalcreative.appguru.utils.helper.loadingDialog
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_assignment.*
import kotlinx.android.synthetic.main.toolbar.*

@AndroidEntryPoint
class AssignmentActivity : AppCompatActivity(), AssignmentAdapter.ClickListener {

    private val viewModel by viewModels<AssignmentViewModel>()
    private val loadingDialog by loadingDialog()
    private val assignmentAdapter = AssignmentAdapter()
    private val addAssignmentResults =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            this::handleResultIntent
        )

    private lateinit var classroom: Classroom

    companion object {
        const val EXTRA_CLASSROOM = "extra_classroom"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assignment)
        setSupportActionBar(toolbar)

        classroom = intent.getParcelableExtra(EXTRA_CLASSROOM) ?: return

        initObservers()
        viewModel.getAssignmentByClassroom(classroom.id)

        assignmentAdapter.listener = this

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title = classroom.name
        }

        rv_tugas.apply {
            adapter = assignmentAdapter
            layoutManager = LinearLayoutManager(this@AssignmentActivity)
            setHasFixedSize(true)
        }

        fab_menu.setClosedOnTouchOutside(true)

        fab_murid.setOnClickListener {
            fab_menu.toggle(true)

            val intent = Intent(this, StudentActivity::class.java).apply {
                putExtra(StudentActivity.EXTRA_CLASS_ID, classroom.id)
            }
            startActivity(intent)
        }

        fab_tugas.setOnClickListener {
            fab_menu.toggle(true)

            val intent = Intent(this, AddAssignmentActivity::class.java).apply {
                putExtra(AddAssignmentActivity.EXTRA_ID, classroom.id)
            }
            addAssignmentResults.launch(intent)
        }

        fab_raport.setOnClickListener {
            fab_menu.toggle(true)

            val intent = Intent(this, ReportActivity::class.java).apply {
                putExtra(ReportActivity.EXTRA_ID, classroom.id)
            }
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAssignmentByClassroom(classroom.id)
    }

    override fun onBackPressed() {
        if (fab_menu.isOpened) {
            fab_menu.toggle(true)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_class, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_edit_class) {
            val intent = Intent(this, AddClassroomActivity::class.java).apply {
                putExtra(AddClassroomActivity.EXTRA_TYPE, AddClassroomActivity.TYPE_EDIT)
                putExtra(AddClassroomActivity.EXTRA_CLASS, classroom.id)
                putExtra(AddClassroomActivity.EXTRA_DATA, classroom.name)
            }
            addAssignmentResults.launch(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onItemClicked(assignment: Assignment) {
        val intent = Intent(this, SectionActivity::class.java).apply {
            putExtra(SectionActivity.EXTRA_CLASS_ID, classroom.id)
            putExtra(SectionActivity.EXTRA_ASSIGNMENT, assignment)
        }
        startActivity(intent)
    }

    private fun handleResultIntent(result: ActivityResult) {
        if (result.resultCode == AddAssignmentActivity.RESULT_SUCCESS) {
            result.data?.getStringExtra(AddClassroomActivity.EXTRA_RESULT)?.let {
                supportActionBar?.title = it
            }

            viewModel.getAssignmentByClassroom(classroom.id)
        }
    }

    private fun initObservers() {
        viewModel.loading.observe(this, Observer(this::showLoading))
        viewModel.assignment.observe(this, Observer(this::showAssignment))
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

    private fun showAssignment(assignments: List<Assignment>) {
        assignmentAdapter.apply {
            this.assignments = assignments
            notifyDataSetChanged()
        }
    }

    private fun showErrorMessage(message: String) {
        Toasty.error(this, message, Toasty.LENGTH_LONG, true).show()
    }
}