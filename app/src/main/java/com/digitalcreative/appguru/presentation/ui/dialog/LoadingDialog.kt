package com.digitalcreative.appguru.presentation.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.digitalcreative.appguru.R

class LoadingDialog(private val fm: FragmentManager) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = false
        return inflater.inflate(R.layout.dialog_loading, container, false)
    }

    fun showDialog() {
        show(fm, null)
    }

    fun closeDialog() {
        dismiss()
    }
}