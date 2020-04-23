package edu.andrews.reise.quickquizzer;

import androidx.fragment.app.Fragment;

public class QuestionListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new QuestionListFragment();
    }
}