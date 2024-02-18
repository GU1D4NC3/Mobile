package com.momground.android.data;

import com.momground.android.common.ViewType;

import java.util.Date;

public class ResultTrainingData implements ViewType {
    private LevelLabel levelLabel;
    private Date date;
    private int score;
    private int maxScore;
    private int trainingDataId; // 진짜 훈련 데이터의 id임
    private int viewType;

    public ResultTrainingData() {

    }

    public ResultTrainingData(LevelLabel levelLabel, Date date, int score, int maxScore, int trainingDataId) {
        this.levelLabel = levelLabel;
        this.date = date;
        this.score = score;
        this.maxScore = maxScore;
        this.trainingDataId = trainingDataId;
    }

    public ResultTrainingData(LevelLabel levelLabel, Date date, int score, int maxScore, int trainingDataId, int viewType) {
        this.levelLabel = levelLabel;
        this.date = date;
        this.score = score;
        this.maxScore = maxScore;
        this.trainingDataId = trainingDataId;
        this.viewType = viewType;
    }

    public LevelLabel getLevelLabel() {
        return levelLabel;
    }

    public void setLevelLabel(LevelLabel levelLabel) {
        this.levelLabel = levelLabel;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public int getTrainingDataId() {
        return trainingDataId;
    }

    public void setTrainingDataId(int trainingDataId) {
        this.trainingDataId = trainingDataId;
    }

    @Override
    public int getViewType() {
        return 0;
    }
}