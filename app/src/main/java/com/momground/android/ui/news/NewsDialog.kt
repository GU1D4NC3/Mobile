package com.momground.android.ui.news

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.momground.android.R
import com.momground.android.ui.news.quiz.QuizDialog

/**
 * NewsLetter Dialog (Fullscreen)
 */
class NewsDialog(val fm: FragmentManager, private val layoutResId: Int, val title: String, val category: String, val content: String) : DialogFragment() {

    lateinit var dialogView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialogView = inflater.inflate(layoutResId, container, false)

        dialogView.findViewById<TextView>(R.id.title).text = title
        dialogView.findViewById<TextView>(R.id.category).text = category
        dialogView.findViewById<TextView>(R.id.content).text = content
        dialogView.findViewById<RelativeLayout>(R.id.button).setOnClickListener {

            val progressDialog: QuizDialog by lazy { QuizDialog(fm, R.layout.dialog_quiz) }
            if (!progressDialog.isAdded) {
                progressDialog.show(fm, null)
            }

            if (progressDialog.isAdded) {
                progressDialog.dismiss()
            }
        }

        return dialogView
    }

    override fun onResume() {
        super.onResume()

        // full Screen code
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }
}