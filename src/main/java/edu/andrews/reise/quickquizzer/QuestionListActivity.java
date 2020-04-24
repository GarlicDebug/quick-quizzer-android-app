package edu.andrews.reise.quickquizzer;

import androidx.fragment.app.Fragment;

/**
 * Activity that hosts Question List fragment
 */
public class QuestionListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new QuestionListFragment();
    }
}