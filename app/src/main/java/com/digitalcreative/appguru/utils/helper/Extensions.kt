package com.digitalcreative.appguru.utils.helper

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
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

fun Activity.isCameraPermissionAllowed(): Boolean {
    return (ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED)
}

fun Activity.isStoragePermissionAllowed(): Boolean {
    return (ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED)
}