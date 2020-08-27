package com.digitalcreative.appguru.presentation.ui.question

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.GroupAnswer
import com.digitalcreative.appguru.presentation.adapter.GroupAnswerAdapter
import com.digitalcreative.appguru.utils.helper.loadingDialog
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_add_question.*
import kotlinx.android.synthetic.main.toolbar.*

@AndroidEntryPoint
class AddQuestionActivity : AppCompatActivity(), GroupAnswerAdapter.OnClickListener {

    private val viewModel by viewModels<QuestionViewModel>()
    private val loadingDialog by loadingDialog()
    private val groupAnswerAdapter = GroupAnswerAdapter()

    private lateinit var manager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_question)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title = getString(R.string.tambah_soal)
        }

        groupAnswerAdapter.listener = this
        manager =
            LinearLayoutManager(this@AddQuestionActivity, LinearLayoutManager.HORIZONTAL, false)

        rv_group_answer.apply {
            adapter = groupAnswerAdapter
            layoutManager = manager
            setHasFixedSize(true)
        }

        initObservers()
        viewModel.getAssignmentQuestionChoice()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onItemClicked(id: String, position: Int?) {
        position?.let { uncheckChip(position) }
    }

    private fun uncheckChip(position: Int) {
        val chipGroup = manager.findViewByPosition(position) as ChipGroup
        val chip = chipGroup.getChildAt(0) as Chip
        chip.isChecked = false
    }

    private fun initObservers() {
        viewModel.loading.observe(this, Observer(this::showLoading))
        viewModel.choice.observe(this, Observer(this::showChoices))
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

    private fun showChoices(choices: List<GroupAnswer>) {
        groupAnswerAdapter.apply {
            groupAnswers = choices
            notifyDataSetChanged()
        }
    }

    private fun showErrorMessage(message: String) {
        Toasty.error(this, message, Toasty.LENGTH_LONG, true).show()
    }
}