package com.momground.android.ui.health

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.momground.android.R
import com.momground.android.common.BaseFragment
import com.momground.android.databinding.FragmentHealthDietBinding
import com.momground.android.ui.health.upload.NutriantDialog

class HealthDietFragment: BaseFragment(), View.OnClickListener {
    private var _binding: FragmentHealthDietBinding? = null
    private val binding get() = _binding!!
    private lateinit var timer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHealthDietBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.itemNone0.setOnClickListener(this)

        return root
    }

    override fun onClick(view: View?) {
        when(view){
            binding.itemNone0 -> openUpload()
        }
    }

    private fun openUpload(){
        val progressDialog: NutriantDialog by lazy { NutriantDialog(parentFragmentManager, R.layout.dialog_upload) }
        if (!progressDialog.isAdded) {
            progressDialog.show(parentFragmentManager, null)
        }

        if (progressDialog.isAdded) {
            progressDialog.dismiss()
            binding.itemNone0.visibility = View.GONE
            binding.tempLunch.visibility = View.VISIBLE
        }
    }

    internal fun newInstant() : HealthDietFragment
    {
        val args = Bundle()
        val frag = HealthDietFragment()
        frag.arguments = args
        return frag
    }
}