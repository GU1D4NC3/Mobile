package com.momground.android.ui.health

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.momground.android.common.BaseFragment
import com.momground.android.databinding.FragmentHealthHospitalBinding

class HealthHospitalFragment: BaseFragment(), View.OnClickListener {
        private var _binding: FragmentHealthHospitalBinding? = null
        private val binding get() = _binding!!

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {

            _binding = FragmentHealthHospitalBinding.inflate(inflater, container, false)
            val root: View = binding.root

            return root
        }

    override fun onClick(view: View?) {

    }

    internal fun newInstant() : HealthHospitalFragment
    {
        val args = Bundle()
        val frag = HealthHospitalFragment()
        frag.arguments = args
        return frag
    }
}