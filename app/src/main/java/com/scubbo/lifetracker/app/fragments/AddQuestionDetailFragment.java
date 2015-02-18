package com.scubbo.lifetracker.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.scubbo.lifetracker.app.storage.DatabaseHelper;
import com.scubbo.lifetracker.app.R;
import com.scubbo.lifetracker.app.questions.QuestionType;

public class AddQuestionDetailFragment extends Fragment {

    private DatabaseHelper dbHelper;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        dbHelper = DatabaseHelper.getInstance(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_question_detail, container, false);

        Button button = (Button) rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText questionText = (EditText) ((View)view.getParent()).findViewById(R.id.editText);
                String questionTextValue = questionText.getText().toString();
                addQuestion(questionTextValue);
                questionText.setText("");
            }
        });
        return rootView;
    }

    private void addQuestion(String questionText) {
        dbHelper.addQuestion(QuestionType.BOOLEAN, questionText);
    }
}
