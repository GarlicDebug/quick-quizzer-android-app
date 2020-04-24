package edu.andrews.reise.quickquizzer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import androidx.fragment.app.Fragment;

import java.util.UUID;

/**
 * Show the details for a question and allow editing
 * A simple {@link Fragment} subclass.
 */
public class QuestionDetailsFragment extends Fragment {

    /** Tag for logging fragment messages */
    public static final String TAG = "QuestionDetailsFragment";
    /** Question that is being viewed/edited */
    private Question mQuestion;
    /** Reference to title field for question */
    private EditText mTitleField;
    /**Reference to question solution switch */
    private Switch mSolutionSwitch;

    public QuestionDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Create a new QuestionDetailsFragment with a given Question id as an argument
     * @param questionId
     * @return a reference to the new QuestionDetailsFragment
     */
    public static QuestionDetailsFragment newInstance(UUID questionId){
        //Create a new argument Bundle object.
        //Add the Question id as an argument.
        Bundle args = new Bundle();
        args.putSerializable(QuestionAdapter.EXTRA_QUESTION_ID, questionId);
        //Create new instance of QuestionDetailsFragment
        QuestionDetailsFragment fragment = new QuestionDetailsFragment();
        //Pass the bundle (containing question id) to the fragment
        //The bundle will be unpacked in the fragment's onCreate(Bundle) method
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //Extract question id from Bundle
        UUID questionId = (UUID)getArguments().getSerializable(QuestionAdapter.EXTRA_QUESTION_ID);

        //Get the question with the id from the Bundle
        //This will be the question that the fragment displays.
        mQuestion = QuestionList.getInstance(getActivity()).getQuestion(questionId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_question_details, container, false);

        // get reference to EditText box for question body
        mTitleField = v.findViewById(R.id.question_body_text);
        mTitleField.setText(mQuestion.getBody());
        mTitleField.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){
                //intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){
                // user typed text, update the question title
                mQuestion.setBody(s.toString());
                // write the new title to the message log for debugging
                Log.d(TAG, "Title changed to " + mQuestion.getBody());
            }

            @Override
            public void afterTextChanged(Editable s){
                //left intentionally blank
            }
        });

        //get reference to solution switch
        mSolutionSwitch = v.findViewById(R.id.question_solution_switch);
        mSolutionSwitch.setChecked(mQuestion.getSolution());

        //toggle question solved status when check box is checked
        mSolutionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //set the question's solution
                mQuestion.setSolution(isChecked);
                Log.d(TAG, "Set solution to "+isChecked);
            }
        });

        return v;
    }

    /**
     * Save the question list to a JSON file when app is paused
     */
    @Override
    public void onPause(){
        super.onPause();
        QuestionList.getInstance(getActivity()).saveQuestions();
    }

}
