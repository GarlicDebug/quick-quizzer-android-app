package edu.andrews.reise.quickquizzer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Manage information about a specified question
 */
public class Question {
    /** Unique ID for the question */
    private UUID mID;
    /** Body text of the question */
    private String mBody;
    /** True/False solution to the question */
    private Boolean mSolution = false;

    /** JSON attribute for the Question ID */
    private static final String JSON_ID = "id";
    /** JSON attribute for the Question Body Text */
    private static final String JSON_BODY = "body";
    /** JSON attribute for the Question Solution */
    private static final String JSON_SOLUTION = "solution";

    //TODO: add possible other attributes: subject, scoring system (point weights)

    /**
     * Create and initialize a new question
     */
    public Question(){
        mID = UUID.randomUUID();
    }

    /**
     * Initialize a new question from a JSON object
     * @param json is the JSON object for a question
     * @throws JSONException
     */
    public Question(JSONObject json) throws JSONException {
        mID = UUID.fromString(json.getString(JSON_ID));
        mBody = json.optString(JSON_BODY);
        mSolution = json.getBoolean(JSON_SOLUTION);
    }

    /**
     * Write the question to a JSON object
     * @return JSON object containing the question information
     * @throws JSONException
     */
    public JSONObject toJSON() throws JSONException{
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(JSON_ID, mID.toString());
        jsonObject.put(JSON_BODY, mBody);
        jsonObject.put(JSON_SOLUTION, mSolution);

        return jsonObject;
    }

    //Getters and Setters

    public UUID getID() {
        return mID;
    }

    public void setID(UUID ID) {
        mID = ID;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String body) {
        mBody = body;
    }

    public Boolean getSolution() {
        return mSolution;
    }

    public void setSolution(Boolean solution) {
        mSolution = solution;
    }
}
