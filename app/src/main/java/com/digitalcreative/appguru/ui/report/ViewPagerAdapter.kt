package com.digitalcreative.appguru.ui.report

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.digitalcreative.appguru.ui.report.semester.SemesterFragment

class ViewPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                SemesterFragment().apply {
                    arguments = Bundle().apply {
                        putString(SemesterFragment.TYPE_SEMESTER, SemesterFragment.SEMESTER_1)
                    }
                }
            }
            1 -> {
                SemesterFragment().apply {
                    arguments = Bundle().apply {
                        putString(SemesterFragment.TYPE_SEMESTER, SemesterFragment.SEMESTER_2)
                    }
                }
            }
            else -> {
                SemesterFragment().apply {
                    arguments = Bundle().apply {
                        putString(SemesterFragment.TYPE_SEMESTER, SemesterFragment.SEMESTER_1)
                    }
                }
            }
        }
    }
}