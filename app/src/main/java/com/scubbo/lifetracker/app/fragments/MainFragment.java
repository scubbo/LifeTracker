package com.scubbo.lifetracker.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.scubbo.lifetracker.app.R;
import com.scubbo.lifetracker.app.background.BackgroundServiceInteraction;

public class MainFragment extends Fragment {

    BackgroundServiceInteraction bsi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bsi = new BackgroundServiceInteraction(getActivity());

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        TextView v = (TextView) rootView.findViewById(R.id.textView2);
        TextView v1 = (TextView) rootView.findViewById(R.id.textView3);
        TextView v2 = (TextView) rootView.findViewById(R.id.textViewAskQuestion);

        View.OnTouchListener activityAsListener = (View.OnTouchListener) getActivity();
        v.setOnTouchListener(activityAsListener);
        v1.setOnTouchListener(activityAsListener);
        v2.setOnTouchListener(activityAsListener);

        final Button button1 = (Button) rootView.findViewById(R.id.button1);
        final Button button2 = (Button) rootView.findViewById(R.id.button2);
        View.OnClickListener l = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == button1) {
                    bsi.startIt();
                }
                if (view == button2) {
                    bsi.stopIt();
                }
            }
        };
        button1.setOnClickListener(l);
        button2.setOnClickListener(l);

        return rootView;
    }
}
