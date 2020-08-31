package com.digitalcreative.appguru.presentation.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.digitalcreative.appguru.R
import kotlinx.android.synthetic.main.dialog_conclusion.*

class ConclusionDialog(private val fm: FragmentManager, private val listener: OnClickListener) :
    DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_conclusion, container, false)
    }

    override fun onStart() {
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        super.onStart()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_send.setOnClickListener {
            val conclusion = edt_conclusion.text.toString().trim()
            listener.onSend(conclusion)
        }
    }

    fun showDialog() {
        show(fm, null)
    }

    fun closeDialog() {
        dismiss()
    }

    interface OnClickListener {
        fun onSend(conclusion: String)
    }
}