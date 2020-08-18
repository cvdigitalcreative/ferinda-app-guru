package com.digitalcreative.appguru.ui.home.tugas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.digitalcreative.appguru.R
import kotlinx.android.synthetic.main.activity_tugas.*
import kotlinx.android.synthetic.main.toolbar.*

class TugasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tugas)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title = "Kelas Mawar"
        }

        fab_menu.setClosedOnTouchOutside(true)

        fab_murid.setOnClickListener {
            fab_menu.toggle(true)
        }

        fab_tugas.setOnClickListener {
            fab_menu.toggle(true)
        }

        fab_raport.setOnClickListener {
            fab_menu.toggle(true)
        }
    }
}