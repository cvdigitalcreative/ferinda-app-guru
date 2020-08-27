package com.digitalcreative.appguru.presentation.ui.assignment.section

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
import com.digitalcreative.appguru.presentation.adapter.SectionAdapter
import com.digitalcreative.appguru.presentation.ui.question.QuestionActivity
import com.digitalcreative.appguru.utils.helper.loadingDialog
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_section.*

@AndroidEntryPoint
class SectionFragment : Fragment(), SectionAdapter.OnClickListener {

    private val viewModel by viewModels<SectionViewModel>()
    private val loadingDialog by loadingDialog()
    private val sectionAdapter = SectionAdapter()

    private lateinit var classId: String
    private lateinit var assignmentId: String

    private val addSectionResults =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            this::handleResultIntent
        )

    companion object {
        const val EXTRA_CLASS_ID = "extra_class_id"
        const val EXTRA_ASSIGNMENT_ID = "extra_assignment_ID"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_section, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        classId = arguments?.getString(EXTRA_CLASS_ID) ?: return
        assignmentId = arguments?.getString(EXTRA_ASSIGNMENT_ID) ?: return

        sectionAdapter.listener = this

        rv_assignment_section.apply {
            adapter = sectionAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        fab_assignment_section.setOnClickListener {
            val intent = Intent(requireContext(), AddSectionActivity::class.java).apply {
                putExtra(AddSectionActivity.EXTRA_CLASS_ID, classId)
                putExtra(AddSectionActivity.EXTRA_ASSIGNMENT_ID, assignmentId)
            }
            addSectionResults.launch(intent)
        }

        initObservers()
        viewModel.getAssignmentSection(classId, assignmentId)
    }

    override fun onItemClicked(section: Assignment.Section) {
        val intent = Intent(requireContext(), QuestionActivity::class.java).apply {
            putExtra(QuestionActivity.EXTRA_CLASS_ID, classId)
            putExtra(QuestionActivity.EXTRA_ASSIGNMENT_ID, assignmentId)
        }
        startActivity(intent)
    }

    private fun handleResultIntent(result: ActivityResult) {
        if (result.resultCode == AddSectionActivity.RESULT_SUCCESS) {
            viewModel.getAssignmentSection(classId, assignmentId)
        }
    }

    private fun initObservers() {
        viewModel.loading.observe(viewLifecycleOwner, Observer(this::showLoading))
        viewModel.section.observe(viewLifecycleOwner, Observer(this::showSection))
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

    private fun showSection(sections: List<Assignment.Section>) {
        sectionAdapter.apply {
            this.sections = sections
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