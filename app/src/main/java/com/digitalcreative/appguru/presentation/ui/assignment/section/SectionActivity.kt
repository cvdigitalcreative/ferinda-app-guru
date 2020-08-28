package com.digitalcreative.appguru.presentation.ui.assignment.section

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Assignment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_section.*

@AndroidEntryPoint
class SectionActivity : AppCompatActivity() {

    private lateinit var classId: String
    private lateinit var assignment: Assignment

    companion object {
        const val EXTRA_CLASS_ID = "extra_class_id"
        const val EXTRA_ASSIGNMENT = "extra_assignment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_section)
        setSupportActionBar(toolbar)

        classId = intent.getStringExtra(EXTRA_CLASS_ID) ?: return
        assignment = intent.getParcelableExtra(EXTRA_ASSIGNMENT) ?: return

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title = assignment.title
        }

        view_pager.adapter = ViewPagerAdapter(this, Bundle().apply {
            putString(SectionFragment.EXTRA_CLASS_ID, classId)
            putParcelable(SectionFragment.EXTRA_ASSIGNMENT, assignment)
        })
        TabLayoutMediator(tab_layout, view_pager) { tab, position ->
            tab.text = getString(
                if (position == 0) R.string.list_bagian
                else R.string.tugas_dikumpul
            )
        }.attach()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}