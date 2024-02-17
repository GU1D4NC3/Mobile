package com.momground.android.ui.health

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.momground.android.common.BaseFragment
import com.momground.android.databinding.FragmentHealthHospitalBinding
import com.momground.android.databinding.FragmentHealthTotalBinding

class HealthTotalFragment: BaseFragment(), View.OnClickListener {
    private var _binding: FragmentHealthTotalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHealthTotalBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // score 점수 가져오기

        // 솔루션 결과 가져오기

        return root
    }

    override fun onClick(view: View?) {

    }

    internal fun newInstant() : HealthTotalFragment
    {
        val args = Bundle()
        val frag = HealthTotalFragment()
        frag.arguments = args
        return frag
    }
}