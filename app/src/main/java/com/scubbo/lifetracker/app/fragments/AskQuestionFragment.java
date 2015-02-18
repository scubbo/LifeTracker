package com.scubbo.lifetracker.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.scubbo.lifetracker.app.storage.DatabaseHelper;
import com.scubbo.lifetracker.app.R;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

public class AskQuestionFragment extends Fragment {

    private Integer mQuestionId;

    private DatabaseHelper dbHelper;
    private Calendar gc = GregorianCalendar.getInstance();
    private FragmentTransitionHelper fth = FragmentTransitionHelper.getInstance((FragmentActivity) getActivity());

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        dbHelper = DatabaseHelper.getInstance(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mQuestionId = getArguments().getInt("questionId");

        Map<String, String> questionView = dbHelper.getQuestionView(mQuestionId);

        View rootView = inflater.inflate(R.layout.fragment_ask_question, container, false);
        TextView tw = (TextView) rootView.findViewById(R.id.textView5);
        tw.setText(questionView.get("text"));

        Button button = (Button) rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Switch sw = (Switch) ((View)view.getParent()).findViewById(R.id.switch1);
                dbHelper.addBooleanAnswer(mQuestionId, sw.isChecked(), gc.getTimeInMillis());
                Toast.makeText(getActivity(), "Response saved", Toast.LENGTH_SHORT).show();
                fth.setUpFragmentTransition(getActivity(), "ask-question", MainFragment.class, "main-fragment-tag", true);
                fth.clearBackStack();

            }
        });
        return rootView;
    }

}
