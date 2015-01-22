package com.scubbo.lifetracker.app;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements View.OnTouchListener {

    private static final String MAIN_FRAGMENT_TAG = "main-fragment-tag";
    private static final String ADD_QUESTION_TAG = "add-question-tag";
    private static final String ADD_QUESTION_DETAIL_TAG = "add-question-detail-tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            MainFragment fg = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fg, MAIN_FRAGMENT_TAG)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        FragmentManager fm = getSupportFragmentManager();

        if (view.getId() == R.id.textView2) {
            //http://stackoverflow.com/questions/4932462/animate-the-transition-between-fragments
            //http://trickyandroid.com/fragments-translate-animation/
            Fragment originalFragment = fm.findFragmentByTag(MAIN_FRAGMENT_TAG);
            fm.beginTransaction()
                    .setCustomAnimations(R.animator.slide_in_right,
                            R.animator.slide_out_left,
                            R.animator.slide_in_left,
                            R.animator.slide_out_right)
                        .replace(originalFragment.getId(), Fragment
                                        .instantiate(this, AddQuestionFragment.class.getName()),
                                ADD_QUESTION_TAG)
                    .addToBackStack(null).commit();

        }
        if (view.getId() == R.id.textViewBoolean) {
            Fragment originalFragment = fm.findFragmentByTag(ADD_QUESTION_TAG);
            fm.beginTransaction()
                    .setCustomAnimations(R.animator.slide_in_right,
                            R.animator.slide_out_left,
                            R.animator.slide_in_left,
                            R.animator.slide_out_right)
                    .replace(originalFragment.getId(), Fragment
                                    .instantiate(this, AddQuestionDetailFragment.class.getName()),
                            ADD_QUESTION_DETAIL_TAG)
                    .addToBackStack(null).commit();
        }
        return false;
    }


    public static class MainFragment extends Fragment {

        public MainFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            TextView v = (TextView) rootView.findViewById(R.id.textView2);
            TextView v1 = (TextView) rootView.findViewById(R.id.textView3);

            View.OnTouchListener activityAsListener = (View.OnTouchListener) getActivity();
            v.setOnTouchListener(activityAsListener);
            v1.setOnTouchListener(activityAsListener);

            return rootView;
        }
    }

    public static class AddQuestionFragment extends Fragment {
        public AddQuestionFragment() {}

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

    public static class AddQuestionDetailFragment extends Fragment {
        //TODO: Make this configurable
        public AddQuestionDetailFragment() {}

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
                }
            });
            return rootView;
        }

        private void addQuestion(String questionText) {
            System.out.println("DEBUG - we would add question with text " + questionText + " but I haven't done that yet");
        }
    }
}
