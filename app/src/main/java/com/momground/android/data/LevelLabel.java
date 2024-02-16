package com.momground.android.data;

import java.io.Serializable;

/**
 * Represent common three level (easy, normal, hard)
 */
public enum LevelLabel implements Serializable {
    EASY(0, "초급"), NORMAL(1, "중급"), HARD(2, "고급"), RECOMMEND(3, "추천"), FIXED(4,"고정");
    
    private int index;
    private String text;
    
    LevelLabel(int index, String text) {
        this.index = index;
        this.text = text;
    }
    
    public int getIndex() {
        return index;
    }
    
    public String getText() {
        return text;
    }

    public static LevelLabel getLabel(int index) {
        if (index == 0) return EASY;
        else if (index == 1) return EASY;
        else if (index == 2) return NORMAL;
        else if (index == 3) return HARD;
        return null;
    }
    public static LevelLabel getLabel(String index) {
        if (index.equals(LevelLabel.EASY.getText())) return EASY;
        if (index.equals(LevelLabel.NORMAL.getText())) return NORMAL;
        if (index.equals(LevelLabel.HARD.getText())) return HARD;
        return null;
    }
}
