package com.momground.android.ui.health

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import com.momground.android.R
import com.momground.android.databinding.FragmentHealthBinding

class HealthFragment : Fragment() {

    private var _binding: FragmentHealthBinding? = null
    private val menuName = arrayListOf(R.string.sample , R.string.sample , R.string.sample)

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[HealthViewModel::class.java]

        _binding = FragmentHealthBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val pagerAdapter = FragmentAdapter(childFragmentManager)
        binding.viewPager.adapter = pagerAdapter
        binding.tab.setupWithViewPager( binding.viewPager)

        return root
    }

    class FragmentAdapter (fm : FragmentManager): FragmentPagerAdapter(fm) {
        //position 에 따라 원하는 Fragment로 이동시키기
        override fun getItem(position: Int): Fragment {
            val fragment =  when(position)
            {
                0->HealthTotalFragment().newInstant()
                1-> HealthDietFragment().newInstant()
                2-> HealthHospitalFragment().newInstant()
                else -> HealthTotalFragment().newInstant()
            }
            return fragment
        }

        override fun getCount(): Int = 3

        override fun getPageTitle(position: Int): CharSequence? {
            /*
            val title = when(position)
            {
                0-> R.string.title_health_total
                1-> R.string.title_health_diet
                2-> R.string.title_health_hospital
                else -> R.string.title_health_total
            }
             */

            val title = when(position)
            {
                0-> "종합"
                1-> "식단 기록"
                2-> "병원 연계(TBD)"
                else -> "종합"
            }
            return title
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}