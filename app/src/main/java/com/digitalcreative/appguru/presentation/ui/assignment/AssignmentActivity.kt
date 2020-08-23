package com.digitalcreative.appguru.presentation.ui.assignment

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Assignment
import com.digitalcreative.appguru.data.model.Classroom
import com.digitalcreative.appguru.presentation.adapter.AssignmentAdapter
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

    companion object {
        const val EXTRA_CLASSROOM = "extra_classroom"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assignment)
        setSupportActionBar(toolbar)

        val classroom = intent.getParcelableExtra<Classroom>(EXTRA_CLASSROOM) ?: return

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
        }

        fab_tugas.setOnClickListener {
            fab_menu.toggle(true)
        }

        fab_raport.setOnClickListener {
            fab_menu.toggle(true)
        }
    }

    override fun onItemClicked(assignment: Assignment) {
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