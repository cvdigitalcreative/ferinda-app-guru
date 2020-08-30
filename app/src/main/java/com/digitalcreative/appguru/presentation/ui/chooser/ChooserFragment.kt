package com.digitalcreative.appguru.presentation.ui.chooser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.digitalcreative.appguru.R
import kotlinx.android.synthetic.main.fragment_chooser.*


class ChooserFragment(private val listener: ChooserListener) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chooser, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.let {
            btn_choose_file.setOnClickListener { listener.onChooseFileClicked() }
            btn_take_picture.setOnClickListener { listener.onTakePictureClicked() }
        }
    }

    interface ChooserListener {
        fun onChooseFileClicked()
        fun onTakePictureClicked()
    }
}
