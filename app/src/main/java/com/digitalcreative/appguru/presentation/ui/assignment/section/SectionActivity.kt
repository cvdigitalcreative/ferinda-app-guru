package com.digitalcreative.appguru.presentation.ui.assignment.section

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Assignment
import com.digitalcreative.appguru.presentation.ui.assignment.AddAssignmentActivity
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_section.*

@AndroidEntryPoint
class SectionActivity : AppCompatActivity() {

    private lateinit var classId: String
    private lateinit var assignment: Assignment
    private val editAssignmentResults =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            this::handleResultIntent
        )

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_assignment, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_edit_assignment) {
            val intent = Intent(this, AddAssignmentActivity::class.java).apply {
                putExtra(AddAssignmentActivity.EXTRA_TYPE, AddAssignmentActivity.TYPE_EDIT)
                putExtra(AddAssignmentActivity.EXTRA_ID, classId)
                putExtra(AddAssignmentActivity.EXTRA_ASSIGNMENT_ID, assignment.id)
                putExtra(AddAssignmentActivity.EXTRA_TITLE, assignment.title)
                putExtra(AddAssignmentActivity.EXTRA_DESCRIPTION, assignment.description)
            }
            editAssignmentResults.launch(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun handleResultIntent(result: ActivityResult) {
        if (result.resultCode == AddAssignmentActivity.RESULT_SUCCESS) {
            result.data?.getStringExtra(AddAssignmentActivity.EXTRA_RESULT)?.let {
                supportActionBar?.title = it
            }
        }
    }
}