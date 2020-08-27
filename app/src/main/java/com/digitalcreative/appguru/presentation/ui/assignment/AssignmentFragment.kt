package com.digitalcreative.appguru.presentation.ui.assignment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Assignment
import com.digitalcreative.appguru.data.model.Classroom
import com.digitalcreative.appguru.presentation.adapter.AssignmentAdapter
import com.digitalcreative.appguru.presentation.ui.assignment.section.SectionActivity
import com.digitalcreative.appguru.presentation.ui.student.AddStudentActivity
import com.digitalcreative.appguru.utils.helper.loadingDialog
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_assignment.*

@AndroidEntryPoint
class AssignmentFragment : Fragment(), AssignmentAdapter.ClickListener {

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_assignment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        classroom = arguments?.getParcelable(EXTRA_CLASSROOM) ?: return

        initObservers()
        viewModel.getAssignmentByClassroom(classroom.id)

        assignmentAdapter.listener = this

        (requireActivity() as AssignmentActivity).supportActionBar?.apply {
            title = classroom.name
        }

        rv_tugas.apply {
            adapter = assignmentAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        fab_menu.setClosedOnTouchOutside(true)

        fab_murid.setOnClickListener {
            fab_menu.toggle(true)

            val intent = Intent(requireContext(), AddStudentActivity::class.java)
            startActivity(intent)
        }

        fab_tugas.setOnClickListener {
            fab_menu.toggle(true)

            val intent = Intent(requireContext(), AddAssignmentActivity::class.java).apply {
                putExtra(AddAssignmentActivity.EXTRA_ID, classroom.id)
            }
            addAssignmentResults.launch(intent)
        }

        fab_raport.setOnClickListener {
            fab_menu.toggle(true)
        }
    }

    override fun onItemClicked(assignment: Assignment) {
        val intent = Intent(requireContext(), SectionActivity::class.java).apply {
            putExtra(SectionActivity.EXTRA_CLASS_ID, classroom.id)
            putExtra(SectionActivity.EXTRA_ASSIGNMENT, assignment)
        }
        startActivity(intent)
    }

    private fun handleResultIntent(result: ActivityResult) {
        if (result.resultCode == AddAssignmentActivity.RESULT_SUCCESS) {
            viewModel.getAssignmentByClassroom(classroom.id)
        }
    }

    private fun initObservers() {
        viewModel.loading.observe(viewLifecycleOwner, Observer(this::showLoading))
        viewModel.assignment.observe(viewLifecycleOwner, Observer(this::showAssignment))
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer(this::showErrorMessage))
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
        Toasty.error(requireContext(), message, Toasty.LENGTH_LONG, true).show()
    }
}