package edu.andrews.reise.quickquizzer;

import androidx.fragment.app.Fragment;

/**
 * Activity that hosts Quizzing Screen fragment
 */
public class QuizzingScreenActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {return new QuizzingScreenFragment();}
}
