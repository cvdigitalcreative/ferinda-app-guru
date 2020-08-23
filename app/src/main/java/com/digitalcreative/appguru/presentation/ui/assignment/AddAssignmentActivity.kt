package com.digitalcreative.appguru.presentation.ui.assignment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.digitalcreative.appguru.R
import kotlinx.android.synthetic.main.toolbar.*

class AddAssignmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_assignment)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title = "Tambah Tugas"
        }
    }
}