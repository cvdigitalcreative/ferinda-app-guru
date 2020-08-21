package com.digitalcreative.appguru.ui.semester

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.adapter.ReportAdapter
import com.digitalcreative.appguru.data.Report
import kotlinx.android.synthetic.main.fragment_semester.*


class SemesterFragment : Fragment() {

    companion object {
        const val TYPE_SEMESTER = "semester"
        const val SEMESTER_1 = "semester_1"
        const val SEMESTER_2 = "semester_2"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_semester, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val listRaport = listOf(
            Report(1, "Section 1"),
            Report(2, "Section 2"),
            Report(3, "Section 3"),
            Report(4, "Section 4"),
            Report(5, "Section 5")
        )
        val raportAdapter = ReportAdapter()
        raportAdapter.raports = listRaport

        rv_raport.apply {
            adapter = raportAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }
}