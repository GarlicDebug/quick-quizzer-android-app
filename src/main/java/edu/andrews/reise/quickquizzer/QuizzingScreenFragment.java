package edu.andrews.reise.quickquizzer;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

//TODO: Add count of what number question you are on to layout
/**
 * Displays the questions, and handles the answer inputs
 * A simple {@link Fragment} subclass.
 */
public class QuizzingScreenFragment extends Fragment {
    /** Key for the hint about the question stored in Intent sent to QuestionHintActivity. */
    public static final String EXTRA_QUESTION_HINT = "edu.andrews.cptr252.quickquizzer.questionhint";
    /** Used for logging questionIndex*/
    private static final String KEY_QUESTION_INDEX = "questionIndex";
    /** TextView for holding the question text */
    private TextView mQuestionTextView;
    /** Button to choose True as answer */
    private Button mTrueButton;
    /** Button to choose False as answer */
    private Button mFalseButton;
    /** Index used to track current question */
    private int mCurrentIndex = 0;
    /** Array of questions to display */
    private ArrayList<Question> mQuestionList;
    /** String displayed in a toast if answer is correct*/
    private String answerFeedbackCorrect = "Your answer was correct!";
    /** String displayed in a toast if answer is incorrect*/
    private String answerFeedbackIncorrect = "Your answer was incorrect!";


    public QuizzingScreenFragment() {
        // Required empty public constructor
    }

    /**
     * Returns a new instance of the fragment
     * @return new instance of the fragment
     */
    public static Fragment newInstance(){
        Bundle args = new Bundle();
        QuizzingScreenFragment fragment = new QuizzingScreenFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Remember the current question when the activity is destroyed.
     * @param savedInstanceState Bundle used for saving identity of current question.
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        //store index of current question in Bundle.
        //use key to access the value later.
        savedInstanceState.putInt(KEY_QUESTION_INDEX, mCurrentIndex);
    }

    //TODO: add question hints
    //** Launch activity to display question hint */
    /*
    private void displayQuestionHint(){
        Intent i = new Intent(getActivity(), QuestionHintActivity.class);
        i.putExtra(EXTRA_QUESTION_HINT, mQuestionList[mCurrentIndex].getAuthorFact());
        getContext().startActivity(i);
    }
     */

    /** Display the question at the current index */
    private void updateQuestion(){
        String question = mQuestionList.get(mCurrentIndex).getBody();

        mQuestionTextView.setText(question);
    }

    /**
     * Creates a toast to tell the user if their input answer was correct
     * @param answerInput Boolean true or false answer input
     */
    private void giveAnswerFeedback(boolean answerInput){
        if (mQuestionList.get(mCurrentIndex).getSolution() == answerInput){
            Toast.makeText(getActivity(), answerFeedbackCorrect, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getActivity(), answerFeedbackIncorrect, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Setup and inflate layout.
     * @param savedInstanceState Previously saved Bundle
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_quizzing_screen, container, false);

        mQuestionList = QuestionList.getInstance(getActivity()).getQuestions();

        //redisplay the question that was displayed before the activity was destroyed
        if (savedInstanceState != null){
        mCurrentIndex = savedInstanceState.getInt(KEY_QUESTION_INDEX);
        }

        updateQuestion();

        mQuestionTextView = v.findViewById(R.id.questionBodyTextView);
        mTrueButton = v.findViewById(R.id.trueButton);
        mFalseButton = v.findViewById(R.id.falseButton);

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex++;
                if (mCurrentIndex == mQuestionList.size()){
                    mCurrentIndex = 0;
                }
                giveAnswerFeedback(true);
                updateQuestion();
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex++;

                if (mCurrentIndex == mQuestionList.size()){
                    mCurrentIndex = 0;
                }
                giveAnswerFeedback(false);
                updateQuestion();
            }
        });

                /*
        mQuestionTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                displayQuestionHint();
            }
        });
         */

        return v;
    }

}


