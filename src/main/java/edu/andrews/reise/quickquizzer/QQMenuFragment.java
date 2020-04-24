package edu.andrews.reise.quickquizzer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class QQMenuFragment extends Fragment {
    /** Button to move to next quote */
    private Button quizStartButton;
    /** Button to move to next quote */
    private Button questionEditButton;
    /** Button to open bonus content */
    private Button bonusButton;

    public QQMenuFragment() {
        // Required empty public constructor
    }

    /** Set up and inflate the layout */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_q_q_menu, container, false);


        quizStartButton = v.findViewById(R.id.beginButton);
        questionEditButton = v.findViewById(R.id.editButton);
        bonusButton = v.findViewById(R.id.bonusButton);

        quizStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayQuizzingScreen();
            }
        });

        questionEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayQuestionList();
            }
        });

        bonusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
                startActivity(browserIntent);
            }
        });

        return v;
    }

    /** Launch activity to display quizzing screen */
    private void displayQuizzingScreen(){
        Intent i = new Intent(getActivity(), QuizzingScreenActivity.class);
        getContext().startActivity(i);
    }

    /** Launch activity to display question list */
    private void displayQuestionList(){
        Intent i = new Intent(getActivity(), QuestionListActivity.class);
        getContext().startActivity(i);
    }
}
