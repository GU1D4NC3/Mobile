package com.momground.android.ui.mypage

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.momground.android.R
import com.momground.android.databinding.ItemButtonBinding

class MyPageItemLayout : ConstraintLayout {
    lateinit var title : TextView
    lateinit var action : TextView

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
        getAttrs(attrs)
    }
    
    private val binding: ItemButtonBinding by lazy {
        ItemButtonBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.item_button, this, false)
        )
    }

    private fun initView() {
        addView(binding.root)
        title = findViewById(R.id.title)
        action = findViewById(R.id.button)
    }

    private fun getAttrs(attrs:AttributeSet?){
        //아까 만들어뒀던 속성 attrs 를 참조함
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.MyPageLayout)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs:AttributeSet?, defStyle:Int){
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.MyPageLayout,defStyle,0)
        setTypeArray(typedArray)
    }

    //디폴트 설정
    private fun setTypeArray(typedArray : TypedArray){
        binding.run {
            title.text = typedArray.getText(R.styleable.MyPageLayout_title)
            action.text = typedArray.getText(R.styleable.MyPageLayout_action)
        }
        typedArray.recycle()
    }
}