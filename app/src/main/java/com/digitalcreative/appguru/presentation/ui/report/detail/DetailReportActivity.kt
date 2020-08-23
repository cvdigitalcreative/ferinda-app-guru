package com.digitalcreative.appguru.presentation.ui.report.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Question
import com.digitalcreative.appguru.presentation.adapter.ReportValueAdapter
import kotlinx.android.synthetic.main.activity_detail_report.*
import kotlinx.android.synthetic.main.toolbar.*

class DetailReportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_report)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title = "Semester 1"
        }

        val listQuestion = listOf(
            Question("A"),
            Question("B"),
            Question("C"),
            Question("D")
        )

        val raportAdapter = ReportValueAdapter()
        raportAdapter.questions = listQuestion

        rv_raport_value.apply {
            adapter = raportAdapter
            layoutManager = LinearLayoutManager(this@DetailReportActivity)
            setHasFixedSize(true)
        }
    }
}