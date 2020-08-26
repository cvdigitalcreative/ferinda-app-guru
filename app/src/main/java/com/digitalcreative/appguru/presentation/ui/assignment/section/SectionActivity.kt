package com.digitalcreative.appguru.presentation.ui.assignment.section

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Assignment
import com.digitalcreative.appguru.presentation.adapter.SectionAdapter
import com.digitalcreative.appguru.presentation.ui.question.QuestionActivity
import com.digitalcreative.appguru.utils.helper.loadingDialog
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_section.*
import kotlinx.android.synthetic.main.toolbar.*

@AndroidEntryPoint
class SectionActivity : AppCompatActivity(), SectionAdapter.OnClickListener {

    private val viewModel by viewModels<SectionViewModel>()
    private val loadingDialog by loadingDialog()
    private val sectionAdapter = SectionAdapter()

    private lateinit var classId: String
    private lateinit var assignment: Assignment

    companion object {
        const val EXTRA_CLASS_ID = "extra_class_id"
        const val EXTRA_ASSIGNMENT = "extra_assignment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_section)
        setSupportActionBar(toolbar)

        classId = intent.getStringExtra(EXTRA_CLASS_ID) ?: return
        assignment = intent.getParcelableExtra(EXTRA_ASSIGNMENT) ?: return

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title = assignment.title
        }

        sectionAdapter.listener = this

        rv_assignment_section.apply {
            adapter = sectionAdapter
            layoutManager = LinearLayoutManager(this@SectionActivity)
            setHasFixedSize(true)
        }

        initObservers()
        viewModel.getAssignmentSection(classId, assignment.id)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onItemClicked(section: Assignment.Section) {
        val intent = Intent(this, QuestionActivity::class.java).apply {
            putExtra(QuestionActivity.EXTRA_CLASS_ID, classId)
            putExtra(QuestionActivity.EXTRA_ASSIGNMENT_ID, assignment.id)
            putExtra(QuestionActivity.EXTRA_SECTION, section)
        }
        startActivity(intent)
    }

    private fun initObservers() {
        viewModel.loading.observe(this, Observer(this::showLoading))
        viewModel.section.observe(this, Observer(this::showSection))
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

    private fun showSection(sections: List<Assignment.Section>) {
        sectionAdapter.apply {
            this.sections = sections
            notifyDataSetChanged()
        }
    }

    private fun showErrorMessage(message: String) {
        Toasty.error(this, message, Toasty.LENGTH_LONG, true).show()
    }
}