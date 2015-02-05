package com.scubbo.lifetracker.app;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import com.scubbo.lifetracker.app.fragments.AddQuestionDetailFragment;
import com.scubbo.lifetracker.app.fragments.AddQuestionFragment;
import com.scubbo.lifetracker.app.fragments.MainFragment;
import com.scubbo.lifetracker.app.fragments.ViewQuestionsFragment;

public class MainActivity extends ActionBarActivity implements View.OnTouchListener {

    private static final String MAIN_FRAGMENT_TAG = "main-fragment-tag";
    private static final String ADD_QUESTION_TAG = "add-question-tag";
    private static final String ADD_QUESTION_DETAIL_TAG = "add-question-detail-tag";
    private static final String VIEW_QUESTIONS_TAG = "view-questions-tag";

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
        if (view.getId() == R.id.textView2) {
            setUpFragmentTransition(MAIN_FRAGMENT_TAG, AddQuestionFragment.class, ADD_QUESTION_TAG);
        }
        if (view.getId() == R.id.textView3) {
            setUpFragmentTransition(MAIN_FRAGMENT_TAG, ViewQuestionsFragment.class, VIEW_QUESTIONS_TAG);
        }
        if (view.getId() == R.id.textViewBoolean) {
            setUpFragmentTransition(ADD_QUESTION_TAG, AddQuestionDetailFragment.class, ADD_QUESTION_DETAIL_TAG);
        }
        return false;
    }

    private void setUpFragmentTransition(String originalFragmentTag,
                                         Class newFragmentClass,
                                         String newFragmentTag) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment originalFragment = fm.findFragmentByTag(originalFragmentTag);

        //http://stackoverflow.com/questions/4932462/animate-the-transition-between-fragments
        //http://trickyandroid.com/fragments-translate-animation/
        fm.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_right,
                        R.animator.slide_out_left,
                        R.animator.slide_in_left,
                        R.animator.slide_out_right)
                .replace(originalFragment.getId(), Fragment
                                .instantiate(this, newFragmentClass.getName()),
                        newFragmentTag)
                .addToBackStack(null).commit();
    }

}
