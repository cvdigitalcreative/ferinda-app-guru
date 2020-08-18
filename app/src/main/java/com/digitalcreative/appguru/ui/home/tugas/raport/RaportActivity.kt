package com.digitalcreative.appguru.ui.home.tugas.raport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.digitalcreative.appguru.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_raport.*
import kotlinx.android.synthetic.main.toolbar.*

class RaportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_raport)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title = "Isi Raport"
        }

        view_pager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(tab_layout, view_pager) { tab, position ->
            tab.text = getString(
                if (position == 0) R.string.semester_1
                else R.string.semester_2
            )
        }.attach()
    }
}