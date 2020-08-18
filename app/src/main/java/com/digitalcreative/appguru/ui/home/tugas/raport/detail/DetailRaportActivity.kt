package com.digitalcreative.appguru.ui.home.tugas.raport.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.adapter.RaportValueAdapter
import com.digitalcreative.appguru.data.Question
import kotlinx.android.synthetic.main.activity_detail_raport.*

class DetailRaportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_raport)

        val listQuestion = listOf(
            Question("A"),
            Question("B"),
            Question("C"),
            Question("D")
        )

        val raportAdapter = RaportValueAdapter()
        raportAdapter.questions = listQuestion

        rv_raport_value.apply {
            adapter = raportAdapter
            layoutManager = LinearLayoutManager(this@DetailRaportActivity)
            setHasFixedSize(true)
        }
    }
}