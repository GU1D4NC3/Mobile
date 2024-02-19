package com.momground.android.ui.news

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.momground.android.R

/**
 * NewsLetter Dialog (Fullscreen)
 */
class NewsDialog(private val layoutResId: Int, val title: String, val category: String, val content: String) : DialogFragment() {

    lateinit var dialogView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialogView = inflater.inflate(layoutResId, container, false)

        dialogView.findViewById<TextView>(R.id.title).text = title
        dialogView.findViewById<TextView>(R.id.category).text = category
        dialogView.findViewById<TextView>(R.id.content).text = content
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