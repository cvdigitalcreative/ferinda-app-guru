package com.digitalcreative.appguru.presentation.ui.assignment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Assignment
import com.digitalcreative.appguru.data.model.Student
import com.digitalcreative.appguru.presentation.adapter.StudentAdapter
import com.digitalcreative.appguru.presentation.ui.assignment.section.SectionActivity
import com.digitalcreative.appguru.presentation.ui.assignment.section.SectionViewModel
import com.digitalcreative.appguru.presentation.ui.assignment.section.SubmittedActivity
import com.digitalcreative.appguru.utils.helper.loadingDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_submitted_assignment.*

@AndroidEntryPoint
class SubmittedAssignmentFragment : Fragment(), StudentAdapter.OnClickListener {

    private val viewModel by viewModels<SectionViewModel>()
    private val loadingDialog by loadingDialog()
    private val studentAdapter = StudentAdapter()

    private lateinit var classId: String
    private lateinit var assignment: Assignment
    private lateinit var fabMain: FloatingActionButton

    companion object {
        const val EXTRA_CLASS_ID = "extra_class_id"
        const val EXTRA_ASSIGNMENT = "extra_assignment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_submitted_assignment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        classId = arguments?.getString(EXTRA_CLASS_ID) ?: return
        assignment = arguments?.getParcelable(EXTRA_ASSIGNMENT) ?: return

        fabMain = (requireActivity() as SectionActivity).findViewById(R.id.fab_main)

        studentAdapter.listener = this

        rv_student.apply {
            adapter = studentAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        initObservers()
        viewModel.getAssignmentSubmitted(classId, assignment.id)
    }

    override fun onResume() {
        super.onResume()
        fabMain.hide()
    }

    override fun onItemClicked(student: Student) {
        val intent = Intent(requireContext(), SubmittedActivity::class.java).apply {
            putExtra(SubmittedActivity.EXTRA_CLASS_ID, classId)
            putExtra(SubmittedActivity.EXTRA_ASSIGNMENT_ID, assignment.id)
            putExtra(SubmittedActivity.EXTRA_STUDENT, student)
        }
        startActivity(intent)
    }

    private fun initObservers() {
        viewModel.loading.observe(viewLifecycleOwner, Observer(this::showLoading))
        viewModel.assignmentSubmitted.observe(viewLifecycleOwner, Observer(this::showStudent))
        viewModel.successMessage.observe(viewLifecycleOwner, Observer(this::showSuccessMessage))
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

    private fun showStudent(students: List<Student>) {
        studentAdapter.apply {
            this.students = students
            notifyDataSetChanged()
        }
    }

    private fun showSuccessMessage(message: String) {
        Toasty.success(requireContext(), message, Toasty.LENGTH_LONG, true).show()
    }

    private fun showErrorMessage(message: String) {
        Toasty.error(requireContext(), message, Toasty.LENGTH_LONG, true).show()
    }
}