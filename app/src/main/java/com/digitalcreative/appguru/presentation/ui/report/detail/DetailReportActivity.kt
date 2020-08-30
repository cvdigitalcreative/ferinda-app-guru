package com.digitalcreative.appguru.presentation.ui.report.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Indicator
import com.digitalcreative.appguru.presentation.adapter.ReportIndicatorAdapter
import com.digitalcreative.appguru.presentation.ui.report.ReportViewModel
import com.digitalcreative.appguru.utils.helper.loadingDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_detail_report.*
import kotlinx.android.synthetic.main.toolbar.*

@AndroidEntryPoint
class DetailReportActivity : AppCompatActivity() {

    private val viewModel by viewModels<ReportViewModel>()
    private val loadingDialog by loadingDialog()
    private val reportIndicatorAdapter = ReportIndicatorAdapter()

    private lateinit var reportId: String

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_NAME = "extra_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_report)
        setSupportActionBar(toolbar)

        val semester = intent.getStringExtra(EXTRA_NAME) ?: return
        reportId = intent.getStringExtra(EXTRA_ID) ?: return

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title = semester
        }

        rv_raport_value.apply {
            adapter = reportIndicatorAdapter
            layoutManager = LinearLayoutManager(this@DetailReportActivity)
            setHasFixedSize(true)
        }

        initObservers()
        viewModel.getDetailReport(reportId)
    }

    override fun onBackPressed() {
        showAlertDialog()
    }

    override fun onSupportNavigateUp(): Boolean {
        showAlertDialog()
        return super.onSupportNavigateUp()
    }

    private fun initObservers() {
        viewModel.loading.observe(this, Observer(this::showLoading))
        viewModel.detailReport.observe(this, Observer(this::showDetailReport))
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

    private fun showDetailReport(indicators: List<Indicator>) {
        reportIndicatorAdapter.apply {
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

    private fun showAlertDialog() {
        val builder = MaterialAlertDialogBuilder(this).apply {
            setMessage(getString(R.string.raport_cancel))
            setPositiveButton(getString(R.string.ya)) { _, _ ->
                finish()
            }
            setNegativeButton(getString(R.string.tidak)) { _, _ ->

            }
        }.create()

        builder.show()
    }
}