package com.digitalcreative.appguru.ui.assignment.section

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.digitalcreative.appguru.R
import kotlinx.android.synthetic.main.toolbar.*

class AddSectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_section)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title = "Tambah Bagian"
        }
    }
}