package com.momground.android.common.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.momground.android.databinding.DialogWelcomeBinding

class DrivingDoneDialog(confirmDialogInterface: DialogInterface,
                        private val textTitle: String,
                        private val textDesc: String,
                        cancel: Boolean) : DialogFragment() {

    // 뷰 바인딩 정의
    private var _binding: DialogWelcomeBinding? = null
    private val binding get() = _binding!!

    private var confirmDialogInterface: DialogInterface? = null
    
    private var title: String= ""
    private var message: String = ""
    private var cancel = true
    private var positiveButton: String = "이용 종료"
    private var negativeButton: String = "닫기"

    init {
        this.message = message
        this.cancel = cancel
        this.positiveButton = positiveButton
        this.negativeButton = negativeButton
        this.confirmDialogInterface = confirmDialogInterface
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogWelcomeBinding.inflate(inflater, container, false)
        val view = binding.root

        //배경 설정
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)

        //제목, 내용 문구 설정
        binding.title.text = textTitle
        binding.desc.text = textDesc

        // 확인 버튼 클릭
        binding.button.setOnClickListener {
            this.confirmDialogInterface?.onPositiveButtonClick()
            if(cancel) dismiss()
        }

        /*
        // 취소 버튼 클릭
        if(negativeButton.isNullOrBlank()) binding.buttonNegative.visibility = View.GONE
        binding.buttonNegative.setOnClickListener {
            this.confirmDialogInterface?.onNegativeButtonClick()
            if(cancel) dismiss()
        }
         */

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}