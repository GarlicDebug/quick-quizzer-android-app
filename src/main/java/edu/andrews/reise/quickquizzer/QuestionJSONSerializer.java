package edu.andrews.reise.quickquizzer;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Read/Write a list of questions from/to a JSON file.
 */
public class QuestionJSONSerializer {
    private Context mContext;
    private String mFilename;

    public QuestionJSONSerializer(Context c, String f){
        mContext = c;
        mFilename = f;
    }

    /**
     * Load list of questions from JSON file on local device.
     * @return the list of questions read from file.
     * @throws IOException
     * @throws JSONException
     */
    public ArrayList<Question> loadQuestions() throws IOException, JSONException{
        ArrayList<Question> questions = new ArrayList<>();
        BufferedReader reader = null;
        try{
            // open and read the file into a StringBuilder
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null){
                //add next line from JSON file to StringBuilder
                jsonString.append(line);
            }
            //parse the JSON using JSONTokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();

            //build the array of questions from JSONObjects
            for (int i=0; i<array.length(); i++){
                questions.add(new Question(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e){
            //ignore this one, since it happens when we start fresh
        } finally {
            if (reader != null)
                reader.close();
        }
        return questions;
    }

    /**
     * Write a list of questions to local device
     * @param questions is the list of questions to save.
     * @throws JSONException
     * @throws IOException
     */
    public void saveQuestions(ArrayList<Question> questions) throws JSONException, IOException {
        //build an array in JSON
        JSONArray array = new JSONArray();
        for (Question question : questions)
            array.put(question.toJSON());

        //write the file to disk
        Writer writer = null;
        try{
            OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
