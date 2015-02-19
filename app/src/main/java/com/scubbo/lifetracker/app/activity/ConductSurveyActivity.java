package com.scubbo.lifetracker.app.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import com.scubbo.lifetracker.app.R;
import com.scubbo.lifetracker.app.fragments.AskQuestionFragment;

import java.util.List;

public class ConductSurveyActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conduct_survey);

        List<Integer> questionIds = getIntent().getIntegerArrayListExtra("questionIds");
        Integer questionId = questionIds.get(0);

        if (savedInstanceState == null) {
            AskQuestionFragment fg = new AskQuestionFragment();
            fg.setQuestionId(questionId);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fg, "ask-question-tag")
                    .commit();
        }
    }
}
