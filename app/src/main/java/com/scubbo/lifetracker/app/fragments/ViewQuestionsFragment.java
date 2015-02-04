package com.scubbo.lifetracker.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.scubbo.lifetracker.app.DatabaseHelper;
import com.scubbo.lifetracker.app.R;

import java.util.List;

public class ViewQuestionsFragment extends Fragment {

    private DatabaseHelper dbHelper;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        dbHelper = DatabaseHelper.getInstance(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        DatabaseHelper dbHelper = (DatabaseHelper) getArguments().get("dbHelper");

        View rootView = inflater.inflate(R.layout.fragment_view_questions, container, false);

        LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.linearLayout1);
        List<String> questionTexts = dbHelper.getQuestionTexts();
        for (String questionText: questionTexts) {
            TextView tv = new TextView(getActivity());
            tv.setText(questionText);
            layout.addView(tv);
        }
        return rootView;
    }
}
