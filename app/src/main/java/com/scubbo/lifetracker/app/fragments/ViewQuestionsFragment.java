package com.scubbo.lifetracker.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.scubbo.lifetracker.app.DatabaseHelper;
import com.scubbo.lifetracker.app.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewQuestionsFragment extends Fragment implements View.OnTouchListener {

    private DatabaseHelper dbHelper;
    private FragmentTransitionHelper fth;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        dbHelper = DatabaseHelper.getInstance(activity);
        fth = FragmentTransitionHelper.getInstance((FragmentActivity) activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_view_questions, container, false);

        LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.linearLayout1);
        List<Map<String, String>> questionDatas = dbHelper.getQuestionIdsAndTexts();
        for (Map<String, String> questionData: questionDatas) {
            TextView tv = new TextView(getActivity());
            tv.setText(questionData.get("text"));

            Map<String, Object> viewData = new HashMap<String, Object>();
            viewData.put("questionId", questionData.get("id"));
            tv.setTag(R.string.view_data_tag, viewData);
            tv.setOnTouchListener(this);
            layout.addView(tv);
        }
        return rootView;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Integer questionId = Integer.parseInt((String) ((Map<String, Object>) view.getTag(R.string.view_data_tag)).get("questionId"));


        Bundle args = new Bundle();
        args.putInt("questionId", questionId);
        // UGH make these constants stored somewhere!
        fth.setUpFragmentTransition(getActivity(), "view-questions-tag", AskQuestionFragment.class, "ask-question", args);

        return false;
    }
}
