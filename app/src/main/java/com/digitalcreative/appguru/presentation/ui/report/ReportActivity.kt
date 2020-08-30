package com.digitalcreative.appguru.presentation.ui.report

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Indicator
import com.digitalcreative.appguru.data.model.Semester
import com.digitalcreative.appguru.data.model.Student
import com.digitalcreative.appguru.presentation.adapter.CustomDropdownAdapter
import com.digitalcreative.appguru.presentation.adapter.IndicatorAdapter
import com.digitalcreative.appguru.utils.helper.Constants
import com.digitalcreative.appguru.utils.helper.loadingDialog
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_report.*
import kotlinx.android.synthetic.main.toolbar.*

@AndroidEntryPoint
class ReportActivity : AppCompatActivity(), IndicatorAdapter.OnClickListener {

    private val viewModel by viewModels<ReportViewModel>()
    private val loadingDialog by loadingDialog()
    private val indicatorAdapter = IndicatorAdapter()

    private lateinit var classId: String

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        setSupportActionBar(toolbar)

        classId = intent.getStringExtra(EXTRA_ID) ?: return

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title = getString(R.string.isi_raport)
        }

        indicatorAdapter.listener = this

        rv_indicator.apply {
            adapter = indicatorAdapter
            layoutManager =
                GridLayoutManager(this@ReportActivity, 2, GridLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }

        dropdown_semester.setOnItemClickListener { parent, _, position, _ ->
            val semesterId = (parent.adapter.getItem(position) as Semester).id
            viewModel.semesterId = semesterId
        }

        dropdown_student.setOnItemClickListener { parent, _, position, _ ->
            val studentId = (parent.adapter.getItem(position) as Student).id
            viewModel.studentId = studentId
        }

        btn_next.setOnClickListener {
            val semesterId = viewModel.semesterId
            val studentId = viewModel.studentId
            val listIndicator = viewModel.getIndicatorList()
        }

        initObservers()
        viewModel.getStudentsByClass(classId)
    }

    override fun onItemClicked(id: String) {
        viewModel.addIndicator(id)
    }

    override fun onItemUnClicked(id: String) {
        viewModel.removeIndicator(id)
    }

    private fun initObservers() {
        viewModel.loading.observe(this, Observer(this::showLoading))
        viewModel.semester.observe(this, Observer(this::showSemester))
        viewModel.student.observe(this, Observer(this::showStudent))
        viewModel.indicator.observe(this, Observer(this::showIndicator))
        viewModel.successMessage.observe(this, Observer(this::showSuccessMessage))
        viewModel.errorMessage.observe(this, Observer(this::showErrorMessage))
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

    private fun showSemester(semesters: List<Semester>) {
        val adapterSemester =
            CustomDropdownAdapter(
                this,
                R.layout.dropdown_menu_popup_item,
                semesters,
                Constants.TYPE_DROPDOWN_SEMESTER
            )
        dropdown_semester.setAdapter(adapterSemester)
    }

    private fun showStudent(students: List<Student>) {
        val adapterStudent =
            CustomDropdownAdapter(
                this,
                R.layout.dropdown_menu_popup_item,
                students,
                Constants.TYPE_DROPDOWN_STUDENT
            )
        dropdown_student.setAdapter(adapterStudent)
    }

    private fun showIndicator(indicators: List<Indicator>) {
        indicatorAdapter.apply {
            this.indicators = indicators
            notifyDataSetChanged()
        }
    }

    private fun showSuccessMessage(message: String) {
        Toasty.success(this, message, Toasty.LENGTH_LONG, true).show()
        finish()
    }

    private fun showErrorMessage(message: String) {
        Toasty.error(this, message, Toasty.LENGTH_LONG, true).show()
    }
}