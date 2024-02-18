package com.momground.android.data

import com.momground.android.enum_vo.NutrientDomain

class NutrientScore {
    var nutrientDomain: NutrientDomain? = null

    /**
     * normalized score base on 100
     */
    var score = 0f

    constructor() {}
    constructor(nutrientDomain: NutrientDomain?, score: Float) {
        this.nutrientDomain = nutrientDomain
        this.score = score
    }
}