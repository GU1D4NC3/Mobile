package com.momground.android.data.enum_vo

import android.graphics.Color
import java.io.Serializable

/**
 * 6 types of Nutrient
 */
enum class NutrientDomain(val areaColor: String, val areaText: String) : Serializable {
    PROTEIN("#009688", "단백질"),
    CARBOHYDRATE("#3f51b5", "탄수화물"),
    FAT("#9c27b0", "지방"),
    DIETARY_FIBER("#8bc34a", "식이섬유"),
    ETC("#f44336", "기타"),
    VITAMIN("#795548", "비타민");

    fun getAreaColor(): Int {
        return Color.parseColor(areaColor)
    }
}