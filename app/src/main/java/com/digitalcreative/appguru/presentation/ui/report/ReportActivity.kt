package com.digitalcreative.appguru.presentation.ui.report

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.data.model.Indicator
import com.digitalcreative.appguru.data.model.Report
import com.digitalcreative.appguru.data.model.Semester
import com.digitalcreative.appguru.data.model.Student
import com.digitalcreative.appguru.presentation.adapter.CustomDropdownAdapter
import com.digitalcreative.appguru.presentation.adapter.IndicatorAdapter
import com.digitalcreative.appguru.presentation.ui.chooser.ChooserFragment
import com.digitalcreative.appguru.presentation.ui.report.detail.DetailReportActivity
import com.digitalcreative.appguru.utils.helper.Constants
import com.digitalcreative.appguru.utils.helper.isCameraPermissionAllowed
import com.digitalcreative.appguru.utils.helper.isStoragePermissionAllowed
import com.digitalcreative.appguru.utils.helper.loadingDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_report.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

@AndroidEntryPoint
class ReportActivity : AppCompatActivity(), IndicatorAdapter.OnClickListener,
    ChooserFragment.ChooserListener {

    private val viewModel by viewModels<ReportViewModel>()
    private val loadingDialog by loadingDialog()
    private val indicatorAdapter = IndicatorAdapter()

    private lateinit var classId: String

    private val chooseFileResults =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            this::handleChooseFile
        )

    private val takePictureResults =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            this::handleTakePicture
        )

    companion object {
        const val EXTRA_ID = "extra_id"
        private const val PERMISSION_REQUEST_CODE = 101
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

        img_certificate.setOnClickListener {
            chooseImage()
        }

        btn_next.setOnClickListener {
            val semesterId = viewModel.semesterId
            val studentId = viewModel.studentId

            viewModel.addReport(classId, semesterId, studentId)
        }

        initObservers()
        setupBottomSheetChooser()

        viewModel.getAllSemester()
        viewModel.getReportIndicator()
        viewModel.getStudentsByClass(classId)
    }

    override fun onDestroy() {
        viewModel.clearTempImages()
        super.onDestroy()
    }

    override fun onBackPressed() {
        val behavior = BottomSheetBehavior.from(bottom_sheet)
        if (behavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            hideBottomSheetChooser()
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()
                    && (grantResults[0] != PackageManager.PERMISSION_GRANTED
                            || grantResults[1] != PackageManager.PERMISSION_GRANTED)
                ) {
                    Toast.makeText(this, getString(R.string.permission_error), Toast.LENGTH_LONG)
                        .show()
                } else {
                    showBottomSheetChooser()
                }
            }
        }
    }

    override fun onItemClicked(id: String) {
        viewModel.addIndicator(id)
    }

    override fun onItemUnClicked(id: String) {
        viewModel.removeIndicator(id)
    }

    override fun onChooseFileClicked() {
        val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        chooseFileResults.launch(pickPhoto)
    }

    override fun onTakePictureClicked() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    viewModel.createImageFile()
                } catch (ex: IOException) {
                    Toasty.error(this, Constants.UNKNOWN_ERROR, Toasty.LENGTH_LONG, true).show()
                    null
                }

                photoFile?.also {
                    val photoURI: Uri = androidx.core.content.FileProvider.getUriForFile(
                        this,
                        "com.digitalcreative.appguru.FileProvider",
                        it
                    )
                    takePictureIntent.putExtra(
                        MediaStore.EXTRA_OUTPUT,
                        photoURI
                    )
                    takePictureResults.launch(takePictureIntent)
                }
            }
        }
    }

    private fun handleChooseFile(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            hideBottomSheetChooser()

            val photoUri = result.data?.data

            Glide.with(this)
                .load(photoUri)
                .into(img_certificate)

            photoUri?.let { viewModel.processImage(it) }
        }
    }

    private fun handleTakePicture(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            try {
                hideBottomSheetChooser()
                loadThumbnailImage()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun initObservers() {
        viewModel.loading.observe(this, Observer(this::showLoading))
        viewModel.semester.observe(this, Observer(this::showSemester))
        viewModel.student.observe(this, Observer(this::showStudent))
        viewModel.indicator.observe(this, Observer(this::showIndicator))
        viewModel.report.observe(this, Observer(this::showReport))
        viewModel.successMessage.observe(this, Observer(this::showSuccessMessage))
        viewModel.errorMessage.observe(this, Observer(this::showErrorMessage))
    }

    private fun setupBottomSheetChooser() {
        supportFragmentManager.commit {
            val chooserFragment = ChooserFragment(this@ReportActivity)
            replace(R.id.chooser_container, chooserFragment)
        }
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

    private fun showReport(report: Report) {
        val semester = dropdown_semester.text.toString()
        val intent = Intent(this, DetailReportActivity::class.java).apply {
            putExtra(DetailReportActivity.EXTRA_ID, report.id)
            putExtra(DetailReportActivity.EXTRA_NAME, semester)
        }
        startActivity(intent)
    }

    private fun showSuccessMessage(message: String) {
        Toasty.success(this, message, Toasty.LENGTH_LONG, true).show()
        finish()
    }

    private fun showErrorMessage(message: String) {
        Toasty.error(this, message, Toasty.LENGTH_LONG, true).show()
    }

    private fun chooseImage() {
        if (!isCameraPermissionAllowed() || !isStoragePermissionAllowed()) {
            requestRequiredPermission()
            return
        }

        showBottomSheetChooser()
    }

    private fun requestRequiredPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            PERMISSION_REQUEST_CODE
        )
    }

    private fun showBottomSheetChooser() {
        val behavior = BottomSheetBehavior.from(bottom_sheet)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun hideBottomSheetChooser() {
        val behavior = BottomSheetBehavior.from(bottom_sheet)
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun loadThumbnailImage() {
        CoroutineScope(Dispatchers.Default).launch {
            val image = viewModel.compressImage()

            launch(Dispatchers.Main) {
                Glide.with(this@ReportActivity)
                    .load(image)
                    .into(img_certificate)
            }
        }
    }
}