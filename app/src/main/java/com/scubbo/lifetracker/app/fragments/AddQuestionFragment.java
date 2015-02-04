package com.scubbo.lifetracker.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.scubbo.lifetracker.app.R;

public class AddQuestionFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_question, container, false);

        TextView v = (TextView) rootView.findViewById(R.id.textViewBoolean);

        View.OnTouchListener activityAsListener = (View.OnTouchListener) getActivity();
        v.setOnTouchListener(activityAsListener);

        return rootView;
    }
}
