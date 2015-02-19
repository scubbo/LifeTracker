package com.scubbo.lifetracker.app.questions;

//Data class, mostly to be used for passing data from the database. With data.
public class Question {
    private final Integer mQuestionId;

    public Question(Integer questionId) {
        mQuestionId = questionId;
    }

    public Integer getQuestionId() {
        return mQuestionId;
    }
}
