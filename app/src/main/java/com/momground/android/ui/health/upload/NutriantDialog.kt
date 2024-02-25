package com.momground.android.ui.health.upload

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.momground.android.R

class NutriantDialog(val fm: FragmentManager, private val layoutResId: Int) : DialogFragment(),
    View.OnClickListener {

    lateinit var dialogView: View
    val PERMISSION_REQUEST_CODE = 100
    val PICK_IMAGE_REQUEST = 104
    private lateinit var timer: CountDownTimer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialogView = inflater.inflate(layoutResId, container, false)

        dialogView.findViewById<ImageView>(R.id.upload).setOnClickListener(this)
        dialogView.findViewById<RelativeLayout>(R.id.button).setOnClickListener(this)

        var listMeal: List<String> = resources.getStringArray(R.array.list_meal).toList()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listMeal)
        dialogView.findViewById<Spinner>(R.id.spinner_meal).adapter = adapter

        return dialogView
    }

    private fun clickUpload(){
        val PICK_IMAGE_REQUEST = 1
        openGallery()
    }

    private fun clickSubmit(){
        val progressDialog = MyProgressDialog()
        progressDialog.show(fm, "MyProgressDialog")

        timer = object : CountDownTimer(5000, 100) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = 100 - millisUntilFinished / 50
            }

            override fun onFinish() {
                // Progress가 끝났을 때 처리할 코드
                dismiss()
            }
        }
        timer.start()
    }

    override fun onResume() {
        super.onResume()

        // full Screen code
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

    override fun onClick(view: View?) {
        when(view){
            dialogView.findViewById<ImageView>(R.id.upload) -> clickUpload()
            dialogView.findViewById<RelativeLayout>(R.id.button) -> clickSubmit()
        }
    }

    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImage: Uri? = data.data
            // 선택된 이미지 처리
            handleImage(selectedImage)
        }
    }


    fun handleImage(selectedImage: Uri?) {
        // 선택된 이미지를 ImageView에 설정하거나 다른 처리를 수행합니다.
        dialogView.findViewById<ImageView>(R.id.upload_image).setImageResource(R.drawable.img_meal)
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context!!,
            READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            context!! as Activity,
            arrayOf(READ_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            } else {
                Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

}