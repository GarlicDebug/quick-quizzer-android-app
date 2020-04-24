package edu.andrews.reise.quickquizzer;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Displays the questions, and handles the answer inputs
 * A simple {@link Fragment} subclass.
 */
//TODO: Add count of what number question you are on to layout
    //TODO: add quote hint
public class QuizzingScreenFragment extends Fragment {

    /** ImageView used to display inspirational image */
    private ImageView mImageView;

    /** Key for the fact about the author stored in Intent sent to AuthorFactActivity. */
    public static final String EXTRA_AUTHOR_FACT =
            "edu.andrews.cptr252.rlsummerscales.questionoftheday.author_fact";


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
    /** */
    String answerFeedbackCorrect = "Your answer was correct!";
    String answerFeedbackIncorrect = "Your answer was correct!";


    public QuizzingScreenFragment() {
        // Required empty public constructor
    }

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

    /** Launch activity to display question hint */
    /*
    private void displayQuestionHint(){
        Intent i = new Intent(getActivity(), QuestionHintActivity.class);
        i.putExtra(EXTRA_AUTHOR_FACT, mQuestionList[mCurrentIndex].getAuthorFact());
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
        //setContentView(R.layout.activity_question);

        mQuestionList = QuestionList.getInstance(getActivity()).getQuestions();

        //redisplay the question that was displayed before the activity was destroyed
        if (savedInstanceState != null){
        mCurrentIndex = savedInstanceState.getInt(KEY_QUESTION_INDEX);
        }

        mQuestionTextView = v.findViewById(R.id.questionBodyTextView);

        /*
        mQuestionTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                displayQuestionHint();
            }
        });
         */

        updateQuestion();

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

        return v;
    }

}


