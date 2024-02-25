package com.momground.android.ui.health.upload

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.DialogFragment
import com.momground.android.R

class MyProgressDialog : DialogFragment() {

    private lateinit var timer: CountDownTimer
    private lateinit var view: View
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = inflater.inflate(R.layout.layout_progress, container, false)
        progressBar = view.findViewById(R.id.progressBar)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Progress를 시작하는 코드
        startProgress()
    }

    private fun startProgress() {
        timer = object : CountDownTimer(2700, 100) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = 100 - millisUntilFinished / 50
                progressBar.progress = progress.toInt()
            }

            override fun onFinish() {
                // Progress가 끝났을 때 처리할 코드
                dismiss()
            }
        }
        timer.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer.cancel()
    }
}