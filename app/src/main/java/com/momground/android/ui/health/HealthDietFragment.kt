package com.momground.android.ui.health

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.momground.android.common.BaseFragment
import com.momground.android.databinding.FragmentHealthDietBinding
import com.momground.android.databinding.FragmentHealthTotalBinding

class HealthDietFragment: BaseFragment(), View.OnClickListener {
    private var _binding: FragmentHealthDietBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHealthDietBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onClick(view: View?) {

    }

    internal fun newInstant() : HealthDietFragment
    {
        val args = Bundle()
        val frag = HealthDietFragment()
        frag.arguments = args
        return frag
    }
}