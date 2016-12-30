package com.corelibrary.models;

/**
 * Created by kamalverma on 30/12/16.
 */

public class GenericResponse {

    protected String errorMessage;
    protected int statusCode;


    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
