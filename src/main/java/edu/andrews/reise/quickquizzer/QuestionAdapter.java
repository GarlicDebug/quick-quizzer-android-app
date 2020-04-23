package edu.andrews.reise.quickquizzer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import com.google.android.material.snackbar.Snackbar;

/**
 * Adapter responsible for getting the view for a bug.
 */
public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    public static final String TAG="QuestionAdapter";
    /** key used to pass the id of a question */
    public static final String EXTRA_QUESTION_ID
            ="edu.andrews.quickquizzer.questiontracker.question_id";
    /** Used to store reference to list of questions */
    private ArrayList<Question> mQuestions;

    /**
     * Constructor for QuestionAdapter. Initialize adapter with given list of questions.
     * @param questions list of questions to display
     */
    public QuestionAdapter(ArrayList<Question> questions){
        mQuestions = questions;
    }

    /** Activity hosting the list fragment */
    private Activity mActivity;
    public QuestionAdapter(ArrayList<Question> questions, Activity activity) {
        mQuestions = questions;
        mActivity = activity;
    }
    /** Return reference to activity hosting the question list fragment */
    public Context getActivity() {
        return mActivity;
    }

    /**
     * Create snackbar with ability to undo question deletion.
     */
    private void showUndoSnackbar(final Question question, final int position) {
        // get root view for activity hosting question list fragment
        View view = mActivity.findViewById(android.R.id.content);
        // build message stating which question was deleted
        String questionDeletedText = mActivity.getString(R.string.question_deleted_msg,
                question.getBody());
        // create the snackbar
        Snackbar snackbar = Snackbar.make(view, questionDeletedText, Snackbar.LENGTH_LONG);
        // add the Undo option to the snackbar
        snackbar.setAction(R.string.undo_option, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // undo is selected, restore the deleted item
                restoreQuestion(question, position);
            }
        });
        // Text for UNDO will be yellow
        snackbar.setActionTextColor(Color.YELLOW);
        // display snackbar
        snackbar.show();
    }
    /**
     * Remove question from list
     * @param position index of question to remove
     */
    public void deleteQuestion(int position) {
        // Save deleted question so we can undo delete if needed.
        final Question question = mQuestions.get(position);
        // delete question from list
        QuestionList.getInstance(mActivity).deleteQuestion(position);
        // update list of questions in recyclerview
        notifyItemRemoved(position);
        // display snackbar so user may undo delete
        showUndoSnackbar(question, position);
    }
    /**
     * Put deleted question back into list
     * @param question to restore
     * @param position in list where question will go
     */
    public void restoreQuestion(Question question, int position) {
        QuestionList.getInstance(mActivity).addQuestion(position, question);
        notifyItemInserted(position);
    }


    /**
     * Class to hold references to widgets on a given view.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /** TextView that displays question title */
        public TextView questionTitleTextView;
        /** TextView that displays correct answer */
        public TextView answerTextView;
        /** Content hosting the view */
        public Context context;

        /** Create a new view holder for a given view item in the question list */
        public ViewHolder(View itemView){
            super(itemView);

            // Store references to the widgets on the view item
            questionTitleTextView = itemView.findViewById(R.id.question_list_item_titleTextView);
            answerTextView = itemView.findViewById(R.id.question_answer_textView);

            //Get the context of the view. This will be the activity hosting the view.
            context = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        /**
         * OnClick listener for question in the question list.
         * Triggered when user clicks on a question in the list
         * @param view for the question list that was clicked
         */
        @Override
        public void onClick(View view){
            // Get index of question that was clicked.
            int position = getAdapterPosition();
            // open the selected question.
            if (position != RecyclerView.NO_POSITION){
                Question question = mQuestions.get(position);
                //start and instance of Question Details fragment
                Intent i = new Intent(context, QuestionDetailsActivity.class);
                //pass the id of the question as an intent
                i.putExtra(QuestionAdapter.EXTRA_QUESTION_ID, question.getID());
                context.startActivity(i);
            }
        }
    } // end ViewHolder

    /**
     * Create a new view to display a question.
     * Return the ViewHolder that stores references to the widgets on the new view.
     */
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Get the layout inflater from parent that contains the question view item
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the layout to display a question in the list.
        View questionView = inflater.inflate(R.layout.list_item_question, parent, false);

        // Create a view holder to store references to the widgets on the new view.
        ViewHolder viewHolder = new ViewHolder(questionView);

        return viewHolder;
    }

    /**
     * Display given question in the view referenced by the given ViewHolder.
     * @param viewHolder Contains references to widgets used to display question.
     * @param position Index of the question in the list
     */
    @Override
    public void onBindViewHolder(QuestionAdapter.ViewHolder viewHolder, int position) {
        // Get question for given index in question list
        Question question = mQuestions.get(position);

        // Get references to widgets stored in the ViewHolder
        TextView questionTitleTextView = viewHolder.questionTitleTextView;
        TextView questionAnswerTextView = viewHolder.answerTextView;

        // TODO:
        // Updated widgets on view with question details
        questionTitleTextView.setText(question.getBody());
        questionAnswerTextView.setText(question.getSolution().toString());
    }

    /**
     * Get number of questions in question list.
     */
    @Override
    public int getItemCount(){
        return mQuestions.size();
    }
}
