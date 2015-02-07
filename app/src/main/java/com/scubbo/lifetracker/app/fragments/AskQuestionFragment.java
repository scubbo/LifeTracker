package com.scubbo.lifetracker.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import com.scubbo.lifetracker.app.R;

public class AskQuestionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ask_question, container, false);

        TextView tw = (TextView) rootView.findViewById(R.id.textView5);
        tw.setText("This is some test text");

        Button button = (Button) rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Switch sw = (Switch) ((View)view.getParent()).findViewById(R.id.switch1);
                Boolean isActive = sw.isChecked();
                System.out.println("I would be saving that sw is (isActive) " + (isActive ? "true" : "false"));
            }
        });
        return rootView;
    }

}
