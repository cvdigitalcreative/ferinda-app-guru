package com.digitalcreative.appguru.presentation.ui.answer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Student
import com.digitalcreative.appguru.data.model.Submitted
import com.digitalcreative.appguru.presentation.adapter.AnswerAdapter
import kotlinx.android.synthetic.main.activity_answer.*
import kotlinx.android.synthetic.main.toolbar.*

class AnswerActivity : AppCompatActivity() {

    private val answerAdapter = AnswerAdapter()
    private lateinit var submitted: Submitted.AssignmentSubmitted
    private lateinit var student: Student

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_STUDENT = "extra_student"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)
        setSupportActionBar(toolbar)

        submitted = intent.getParcelableExtra(EXTRA_DATA) ?: return
        student = intent.getParcelableExtra(EXTRA_STUDENT) ?: return

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title = student.name
        }

        answerAdapter.answers = submitted.questions

        tv_section.text = submitted.section

        rv_answer.apply {
            adapter = answerAdapter
            layoutManager = LinearLayoutManager(this@AnswerActivity)
            setHasFixedSize(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}