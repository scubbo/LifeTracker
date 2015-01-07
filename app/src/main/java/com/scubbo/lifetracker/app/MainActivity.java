package com.scubbo.lifetracker.app;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends ActionBarActivity implements View.OnTouchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
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
//            FragmentManager fm = getSupportFragmentManager();
//            FragmentManager.BackStackEntry entry = fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 1);
//            Fragment destination = fm.getFragments().get(entry.getId());
//            System.out.println(destination);
//
//            FragmentTransaction ft = fm.beginTransaction();
//            System.out.println("begun transaction");
//
//            ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
//            System.out.println("customer animations set");
//            ft.replace(R.layout.fragment_main, destination, destination.getClass().getSimpleName());
//            ft.commit();
//            System.out.println("replacement done");

            //http://stackoverflow.com/questions/4932462/animate-the-transition-between-fragments
            //http://trickyandroid.com/fragments-translate-animation/
            String TAG = "Add-question-tag";
            FragmentManager fm = getSupportFragmentManager();
            Fragment originalFragment = fm.findFragmentById(R.layout.fragment_main);
            Fragment f = fm.findFragmentByTag(TAG);
            if (f != null) {
                fm.popBackStack();
            } else {
                fm.beginTransaction()
                        .setCustomAnimations(R.animator.slide_in_right,
                                R.animator.slide_out_right,
                                R.animator.slide_in_right,
                                R.animator.slide_out_right)
                        .add(R.id.container, Fragment
                                        .instantiate(this, AddQuestionFragment.class.getName()),
                                TAG)
                        .addToBackStack(null).commit();
            }

        }
        return false;
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
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

            return rootView;
        }
    }
}
