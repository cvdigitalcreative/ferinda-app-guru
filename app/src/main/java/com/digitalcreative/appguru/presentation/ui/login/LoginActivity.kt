package com.digitalcreative.appguru.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.presentation.ui.classroom.ClassroomActivity
import com.digitalcreative.appguru.utils.helper.loadingDialog
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_login.*

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel>()
    private val loadingDialog by loadingDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (viewModel.isUserLoggedIn()) {
            moveToHome(true)
            finish()
        }

        initObservers()

        btn_login.setOnClickListener {
            val emailNip = edt_nip_email.text.toString().trim()
            val password = edt_password.text.toString().trim()
            viewModel.login(emailNip, password)
        }
    }

    private fun initObservers() {
        viewModel.loading.observe(this, Observer(this::showLoading))
        viewModel.data.observe(this, Observer(this::moveToHome))
        viewModel.message.observe(this, Observer(this::showMessage))
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

    private fun moveToHome(shouldMove: Boolean) {
        if (shouldMove) {
            startActivity(Intent(this@LoginActivity, ClassroomActivity::class.java))
            finish()
        }
    }

    private fun showMessage(message: String) {
        Toasty.error(this, message, Toasty.LENGTH_LONG, true).show()
    }
}