package com.digitalcreative.appguru.ui.raport.semester

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.digitalcreative.appguru.R
import com.digitalcreative.appguru.adapter.RaportAdapter
import com.digitalcreative.appguru.data.Raport
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
            Raport(1, "Section 1"),
            Raport(2, "Section 2"),
            Raport(3, "Section 3"),
            Raport(4, "Section 4"),
            Raport(5, "Section 5")
        )
        val raportAdapter = RaportAdapter()
        raportAdapter.raports = listRaport

        rv_raport.apply {
            adapter = raportAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }
}