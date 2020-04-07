package edu.andrews.reise.quickquizzer;

import androidx.fragment.app.Fragment;

/**
 * Activity that hosts Main Menu fragment
 */
public class QQMenuActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new QQMenuFragment();
    }
}