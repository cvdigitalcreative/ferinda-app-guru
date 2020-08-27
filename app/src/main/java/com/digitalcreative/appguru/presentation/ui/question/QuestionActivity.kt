package com.digitalcreative.appguru.presentation.ui.question

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Assignment
import com.digitalcreative.appguru.presentation.adapter.QuestionAdapter
import com.digitalcreative.appguru.utils.helper.loadingDialog
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.toolbar.*

@AndroidEntryPoint
class QuestionActivity : AppCompatActivity() {

    private val viewModel by viewModels<QuestionViewModel>()
    private val loadingDialog by loadingDialog()
    private val questionAdapter = QuestionAdapter()

    private lateinit var classId: String
    private lateinit var assignmentId: String
    private lateinit var section: Assignment.Section

    private val addQuestionResults =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            this::handleResultIntent
        )

    companion object {
        const val EXTRA_CLASS_ID = "extra_class_id"
        const val EXTRA_ASSIGNMENT_ID = "extra_assignment_id"
        const val EXTRA_SECTION = "extra_section"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        setSupportActionBar(toolbar)

        classId = intent.getStringExtra(EXTRA_CLASS_ID) ?: return
        assignmentId = intent.getStringExtra(EXTRA_ASSIGNMENT_ID) ?: return
        section = intent.getParcelableExtra(EXTRA_SECTION) ?: return

        initObservers()
        viewModel.getAssignmentQuestion(classId, assignmentId, section.id)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title = section.section
        }

        rv_question.apply {
            adapter = questionAdapter
            layoutManager = LinearLayoutManager(this@QuestionActivity)
            setHasFixedSize(true)
        }

        fab_question.setOnClickListener {
            val intent = Intent(this, AddQuestionActivity::class.java).apply {
                putExtra(AddQuestionActivity.EXTRA_CLASS_ID, classId)
                putExtra(AddQuestionActivity.EXTRA_ASSIGNMENT_ID, assignmentId)
                putExtra(AddQuestionActivity.EXTRA_SECTION_ID, section.id)

            }
            addQuestionResults.launch(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun handleResultIntent(result: ActivityResult) {
        if (result.resultCode == AddQuestionActivity.RESULT_SUCCESS) {
            viewModel.getAssignmentQuestion(classId, assignmentId, section.id)
        }
    }

    private fun initObservers() {
        viewModel.loading.observe(this, Observer(this::showLoading))
        viewModel.question.observe(this, Observer(this::showSection))
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

    private fun showSection(questions: List<Assignment.Section.Question>) {
        questionAdapter.apply {
            this.questions = questions
            notifyDataSetChanged()
        }
    }

    private fun showErrorMessage(message: String) {
        Toasty.error(this, message, Toasty.LENGTH_LONG, true).show()
    }
}