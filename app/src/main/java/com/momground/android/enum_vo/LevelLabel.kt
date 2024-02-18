package com.momground.android.enum_vo

import java.io.Serializable

/**
 * Represent common three level (easy, normal, hard)
 */
enum class LevelLabel(val index: Int, val text: String) : Serializable {
    EASY(0, "초급"), NORMAL(1, "중급"), HARD(2, "고급"), RECOMMEND(3, "추천"), FIXED(4, "고정");

    companion object {
        fun getLabel(index: Int): LevelLabel? {
            if (index == 0) return EASY else if (index == 1) return EASY else if (index == 2) return NORMAL else if (index == 3) return HARD
            return null
        }

        fun getLabel(index: String): LevelLabel? {
            if (index == EASY.text) return EASY
            if (index == NORMAL.text) return NORMAL
            return if (index == HARD.text) HARD else null
        }
    }
}