package com.momground.android.data

import com.momground.android.common.ViewType
import com.momground.android.enum_vo.LevelLabel
import java.util.*

class ResultTrainingData : ViewType {
    var levelLabel: LevelLabel? = null
    var date: Date? = null
    var score = 0
    var maxScore = 0
    var trainingDataId // 진짜 훈련 데이터의 id임
            = 0
    private var viewType = 0

    constructor() {}
    constructor(
        levelLabel: LevelLabel?,
        date: Date?,
        score: Int,
        maxScore: Int,
        trainingDataId: Int
    ) {
        this.levelLabel = levelLabel
        this.date = date
        this.score = score
        this.maxScore = maxScore
        this.trainingDataId = trainingDataId
    }

    constructor(
        levelLabel: LevelLabel?,
        date: Date?,
        score: Int,
        maxScore: Int,
        trainingDataId: Int,
        viewType: Int
    ) {
        this.levelLabel = levelLabel
        this.date = date
        this.score = score
        this.maxScore = maxScore
        this.trainingDataId = trainingDataId
        this.viewType = viewType
    }

    fun setViewType(viewType: Int) {
        this.viewType = viewType
    }

    override fun getViewType(): Int {
        return 0
    }
}