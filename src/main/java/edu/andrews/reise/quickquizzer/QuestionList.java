package edu.andrews.reise.quickquizzer;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Manage list of questions. This is a singleton class.
 * Only one instance of this class may be created.
 */
public class QuestionList {
    /**Instance variable for QuestionList */
    private static QuestionList sOurInstance;
    /**List of Questions*/
    private ArrayList<Question> mQuestions;
    /**Reference to information about app environment */
    private Context mAppContext;
    /** Tag for message log */
    private static final String TAG = "QuestionList";
    /** name of JSON file containing list of questions */
    private static final String FILENAME = "questions.json";
    /**Reference to JSON serializer for a list of questions */
    private QuestionJSONSerializer mSerializer;

    /**
     * Write question list ot JSON file.
     * @return true if successful, false otherwise.
     */
    public boolean saveQuestions() {
        try{
            mSerializer.saveQuestions(mQuestions);
            Log.d(TAG, "Questions saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving questions: " + e);
            return false;
        }
    }

    /**
     * Add a question to the list.
     * @param question is the question to add
     */
    public void addQuestion(Question question){
        mQuestions.add(question);
        saveQuestions();
    }

    /**
     * Add a question to the list at given position.
     * @param position is the index for the question to add.
     * @param question is the question to add.
     */
    public void addQuestion(int position, Question question) {
        mQuestions.add(position, question);
        saveQuestions();
    }
    /**
     * Delete a given question from list of questions.
     * @param position is the index of question to delete.
     */
    public void deleteQuestion(int position) {
        mQuestions.remove(position);
        saveQuestions();
    }

    //make a bunch of dummy questions
    private QuestionList(Context appContext){
        mAppContext = appContext;
        //create our serializer to load and save questions
        mSerializer = new QuestionJSONSerializer(mAppContext, FILENAME);

        try{
            //load questions from JSON file
            mQuestions = mSerializer.loadQuestions();
        } catch (Exception e){
            /*
            Unable to load from file, so create empty question list.
            Either file does not exist (okay)
            Or file contains error (not great)
             */
            mQuestions = new ArrayList<>();
            Log.e(TAG, "Error loading questions: " + e);
        }
    }

    /**
     * Return the Question with a given id.
     * @param id Unique id for the question
     * @return The question object or null if not found.
     */
    public Question getQuestion(UUID id) {
        for (Question question : mQuestions){
            if (question.getID().equals(id)) {
                return question;
            }
        }
        return null;
    }

    /**Return one and only one instance of the question list.
     * (If it does not exist, create it)
     * @param c is the Application context
     * @return Reference to the question list
     */

    public static QuestionList getInstance(Context c) {
        if (sOurInstance == null){
            sOurInstance = new QuestionList(c.getApplicationContext());
        }
        return sOurInstance;
    }

    /**
     * Return list of questions.
     * @return Array of Question objects.
     */
    public ArrayList<Question> getQuestions() {return mQuestions;}
}
