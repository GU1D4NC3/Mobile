package com.momground.android.data;

import com.momground.android.enum_vo.NutrientDomain;

public class NutrientScore {

    private NutrientDomain nutrientDomain;

    /**
     * normalized score base on 100
     */
    private float score;

    public NutrientScore() {}

    public NutrientScore(NutrientDomain nutrientDomain, float score) {
        this.nutrientDomain = nutrientDomain;
        this.score = score;
    }
    
    public NutrientDomain getNutrientDomain() {
        return nutrientDomain;
    }
    
    public void setNutrientDomain(NutrientDomain nutrientDomain) {
        this.nutrientDomain = nutrientDomain;
    }
    
    public float getScore() {
        return score;
    }
    
    public void setScore(float score) {
        this.score = score;
    }
}