package com.digitalcreative.appguru.presentation.ui.assignment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Classroom
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_assignment.*
import kotlinx.android.synthetic.main.fragment_assignment.*

@AndroidEntryPoint
class AssignmentActivity : AppCompatActivity() {

    private lateinit var classroom: Classroom

    companion object {
        const val EXTRA_CLASSROOM = "extra_classroom"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assignment)
        setSupportActionBar(toolbar)

        classroom = intent.getParcelableExtra(EXTRA_CLASSROOM) ?: return

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title = classroom.name
        }

        view_pager.adapter = ViewPagerAdapter(this, Bundle().apply {
            putParcelable(AssignmentFragment.EXTRA_CLASSROOM, classroom)
        })
        TabLayoutMediator(tab_layout, view_pager) { tab, position ->
            tab.text = getString(
                if (position == 0) R.string.list_tugas
                else R.string.tugas_dikumpul
            )
        }.attach()
    }

    override fun onBackPressed() {
        if (fab_menu.isOpened) {
            fab_menu.toggle(true)
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}