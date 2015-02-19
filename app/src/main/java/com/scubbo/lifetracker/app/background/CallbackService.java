package com.scubbo.lifetracker.app.background;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.scubbo.lifetracker.app.R;
import com.scubbo.lifetracker.app.activity.ConductSurveyActivity;
import com.scubbo.lifetracker.app.questions.Question;
import com.scubbo.lifetracker.app.survey.Survey;
import com.scubbo.lifetracker.app.survey.SurveyConstructor;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class CallbackService {

    private final Context mContext;
    private final SurveyConstructor surveyConstructor = new SurveyConstructor();
    private NotificationManager mNM;

    public CallbackService(Context context) {
        mContext = context;
        mNM = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
    }

    public void call() {
        System.out.println("CallbackService was called at " + GregorianCalendar.getInstance().getTime());
        Survey survey = surveyConstructor.construct();
        if (!survey.getQuestions().isEmpty()) {
            final int randomId = (int) Math.random() * 100;

            Intent intent = new Intent(mContext, ConductSurveyActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ArrayList<Integer> idList = Lists.newArrayList(
                    Collections2.transform(survey.getQuestions(),
                            new Function<Question, Integer>() {
                                @Override
                                public Integer apply(Question question) {
                                    return question.getQuestionId();
                                }

                                @Override
                                public boolean equals(Object o) {
                                    return false;
                                }
                            }
                    )
            );
            intent.putIntegerArrayListExtra("questionIds", idList);
            PendingIntent pi = PendingIntent.getActivity(mContext, randomId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            // ^^ http://stackoverflow.com/a/23648802/1040915

            Notification noti = new Notification.Builder(mContext)
                    .setContentTitle("Time to take a survey!")
                    .setContentText("THE SUBJECT TEXT")
                    .setSmallIcon(R.drawable.interrobang)
                    .setContentIntent(pi)
                    .setAutoCancel(true) //We want it to disappear once consumed
                    .build();
            mNM.notify(randomId, noti);
        }

    }

}
