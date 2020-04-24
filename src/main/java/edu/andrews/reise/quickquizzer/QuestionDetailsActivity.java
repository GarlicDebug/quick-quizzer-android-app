package edu.andrews.reise.quickquizzer;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import java.util.ArrayList;
import java.util.UUID;

public class QuestionDetailsActivity extends FragmentActivity {
    /** ViewPager component that allows users to browse questions by swiping */
    private ViewPager mViewPager;

    /**Array of questions made by the user*/
    private ArrayList<Question> mQuestions;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //create the ViewPager
        mViewPager = new ViewPager(this);
        //Viewpager needs a resource Id.
        mViewPager.setId(R.id.viewPager);
        //Set the view for this activity to be the ViewPager
        //Previously, it used the activity_fragment layout)
        setContentView(mViewPager);

        //get the list of questions
        mQuestions = QuestionList.getInstance(this).getQuestions();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int i) {
                Question question = mQuestions.get(i);
                //create a new instance of the QuestionDetailsFragment
                //with the question id as an argument.
                return QuestionDetailsFragment.newInstance(question.getID());
            }

            @Override
            public int getCount() {
                return mQuestions.size();
            }
        });

        //QuestionListFragment now launches QuestionDetailsActivity with a specific question id.
        //Get the intent sent to this activity from the QuestionListFragment.
        UUID questionId = (UUID) getIntent().getSerializableExtra(QuestionAdapter.EXTRA_QUESTION_ID);

        //Search through the list of questions until we find the question
        //with the same id as the one extracted from the intent.
        for (int i = 0; i < mQuestions.size(); i++){
            if (mQuestions.get(i).getID().equals(questionId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
