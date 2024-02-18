package com.momground.android.enum_vo;

import android.graphics.Color;

import java.io.Serializable;

/**
 * 6 types of Nutrient
 * 일단 만들어보았음. (훈련 정보에서 사용해야 해서..)
 */

public enum NutrientDomain implements Serializable {

    PROTEIN("#009688", "단백질"),
    CARBOHYDRATE("#3f51b5", "탄수화물"),
    FAT("#9c27b0", "지방"),
    DIETARY_FIBER("#8bc34a", "식이섬유"),
    ETC("#f44336", "기타"),
    VITAMIN("#795548", "비타민");

    public final String areaText;
    public final String areaColor;

    NutrientDomain(String areaColor, String areaText) {
        this.areaColor = areaColor;
        this.areaText = areaText;
    }

    public int getAreaColor() {
        return Color.parseColor(areaColor);
    }
    public String getAreaText() {
        return areaText;
    }
}
