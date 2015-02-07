package com.scubbo.lifetracker.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.scubbo.lifetracker.app.R;

public class MainFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        TextView v = (TextView) rootView.findViewById(R.id.textView2);
        TextView v1 = (TextView) rootView.findViewById(R.id.textView3);
        TextView v2 = (TextView) rootView.findViewById(R.id.textViewAskQuestion);

        View.OnTouchListener activityAsListener = (View.OnTouchListener) getActivity();
        v.setOnTouchListener(activityAsListener);
        v1.setOnTouchListener(activityAsListener);
        v2.setOnTouchListener(activityAsListener);

        return rootView;
    }
}
