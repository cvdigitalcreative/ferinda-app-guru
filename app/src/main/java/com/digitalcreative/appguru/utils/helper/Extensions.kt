package com.digitalcreative.appguru.utils.helper

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.digitalcreative.appguru.presentation.ui.dialog.LoadingDialog


fun FragmentActivity.loadingDialog(): Lazy<LoadingDialog> {
    return lazy {
        LoadingDialog(supportFragmentManager)
    }
}

fun Fragment.loadingDialog(): Lazy<LoadingDialog> {
    return lazy {
        LoadingDialog(childFragmentManager)
    }
}