package com.scubbo.lifetracker.app.survey;

import com.scubbo.lifetracker.app.questions.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

public class SurveyConstructor {

    public Survey construct() {
        Date date = getDate();
        if (date.getSeconds() % 10 == 0) {
            return new Survey(new ArrayList<Question>(){{add(new Question(7));}});
        } else {
            return new Survey(Collections.<Question>emptyList());
        }
    }

    private Date getDate() {
        return GregorianCalendar.getInstance().getTime();
    }
}
