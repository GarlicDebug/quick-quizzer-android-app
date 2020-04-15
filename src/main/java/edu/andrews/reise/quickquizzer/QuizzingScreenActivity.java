package edu.andrews.reise.quickquizzer;

import androidx.fragment.app.Fragment;

public class QuizzingScreenActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {return new QuizzingScreenFragment();}
}
