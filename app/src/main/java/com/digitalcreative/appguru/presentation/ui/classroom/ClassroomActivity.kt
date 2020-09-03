package com.digitalcreative.appguru.presentation.ui.classroom

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Classroom
import com.digitalcreative.appguru.presentation.adapter.ClassroomAdapter
import com.digitalcreative.appguru.presentation.ui.assignment.AssignmentActivity
import com.digitalcreative.appguru.presentation.ui.login.LoginActivity
import com.digitalcreative.appguru.utils.helper.loadingDialog
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_classroom.*
import kotlinx.android.synthetic.main.toolbar.*

@AndroidEntryPoint
class ClassroomActivity : AppCompatActivity(), ClassroomAdapter.OnClickListener {

    private val viewModel by viewModels<ClassroomViewModel>()
    private val loadingDialog by loadingDialog()
    private val classroomAdapter = ClassroomAdapter()
    private val addClassroomResults =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            this::handleResultIntent
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classroom)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
            title = getString(R.string.app_name)
        }

        initObservers()
        viewModel.getAllClassroom()

        classroomAdapter.listener = this

        rv_kelas.apply {
            adapter = classroomAdapter
            layoutManager =
                GridLayoutManager(this@ClassroomActivity, 2, GridLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }

        fab_class.setOnClickListener {
            val intent = Intent(this, AddClassroomActivity::class.java)
            addClassroomResults.launch(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_logout) {
            viewModel.logout()
            showLoginActivity()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onItemClicked(classroom: Classroom) {
        val intent = Intent(this, AssignmentActivity::class.java).apply {
            putExtra(AssignmentActivity.EXTRA_CLASSROOM, classroom)
        }
        startActivity(intent)
    }

    private fun handleResultIntent(result: ActivityResult) {
        if (result.resultCode == AddClassroomActivity.RESULT_SUCCESS) {
            viewModel.getAllClassroom()
        }
    }

    private fun initObservers() {
        viewModel.loading.observe(this, Observer(this::showLoading))
        viewModel.classroom.observe(this, Observer(this::showClassroom))
        viewModel.errorMessage.observe(this, Observer(this::showMessage))
    }

    private fun showLoading(isShow: Boolean) {
        if (isShow) {
            if (!loadingDialog.isAdded) {
                loadingDialog.showDialog()
            }
        } else {
            if (loadingDialog.isAdded) {
                loadingDialog.closeDialog()
            }
        }
    }

    private fun showClassroom(classrooms: List<Classroom>) {
        classroomAdapter.apply {
            this.classrooms = classrooms
            notifyDataSetChanged()
        }
    }

    private fun showMessage(message: String) {
        Toasty.error(this, message, Toasty.LENGTH_LONG, true).show()
    }

    private fun showLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}