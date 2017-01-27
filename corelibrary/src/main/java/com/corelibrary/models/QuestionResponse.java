package com.corelibrary.models;

import java.util.List;

/**
 * Created by kamalverma on 30/12/16.
 */

public class QuestionResponse extends GenericResponse {

    private List<Question> questions;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> categories) {
        this.questions = categories;
    }
}
