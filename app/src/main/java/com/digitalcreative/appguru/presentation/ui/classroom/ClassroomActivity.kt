package com.digitalcreative.appguru.presentation.ui.classroom

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Classroom
import com.digitalcreative.appguru.presentation.adapter.ClassroomAdapter
import com.digitalcreative.appguru.utils.helper.loadingDialog
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_classroom.*

@AndroidEntryPoint
class ClassroomActivity : AppCompatActivity(), ClassroomAdapter.OnClickListener {

    private val viewModel by viewModels<ClassroomViewModel>()
    private val loadingDialog by loadingDialog()
    private val classroomAdapter = ClassroomAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classroom)

        initObservers()
        viewModel.getAllClassroom()

        classroomAdapter.listener = this

        rv_kelas.apply {
            adapter = classroomAdapter
            layoutManager =
                GridLayoutManager(this@ClassroomActivity, 2, GridLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }
    }

    override fun onItemClicked(classId: String) {
        Log.e("ItemClicked", classId)
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
}