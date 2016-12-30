package com.corelibrary.models;

import java.util.List;

/**
 * Created by kamalverma on 30/12/16.
 */

public class SubjectResponse extends GenericResponse {

    private List<Subject> categories;


    public List<Subject> getCategories() {
        return categories;
    }

    public void setCategories(List<Subject> categories) {
        this.categories = categories;
    }
}
