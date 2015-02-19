package com.scubbo.lifetracker.app.survey;

import com.scubbo.lifetracker.app.questions.Question;

import java.util.List;

public class Survey {
    private final List<Question> mQuestions;

    public Survey(List<Question> questions) {
        mQuestions = questions;
    }

    public List<Question> getQuestions() {
        return mQuestions;
    }

}
