package com.corelibrary.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kamalverma on 27/01/17.
 */

/**
 * Quiz ID, Number of question,  total marks, has negative marking,
 * title,  number of attempted question, total time, time used,
 * isAttempted,  score,  attempted question,  right answers, time used
 */

@DatabaseTable(tableName = "quizs")
public class Quiz implements Serializable {

    @DatabaseField
    private String qTitle;


    @DatabaseField
    private String qType;

    @DatabaseField
    private String tags;


    private boolean negative;


    @Expose(serialize = false, deserialize = false)
    @DatabaseField
    private boolean attempted;


    @DatabaseField
    private int catId;

    @DatabaseField
    private int quizId;


    @Expose(serialize = false, deserialize = false)
    @DatabaseField
    private boolean bookmarked;


    @Expose(serialize = false, deserialize = false)
    @DatabaseField
    private String cDate; //Date of creation

    @Expose(serialize = false, deserialize = false)
    @DatabaseField
    private String mDate;  //Date of modification/attempted


    private int totalQuestion;
    private int totalMArks;

    private int scoredMarks;
    private int attemptedQuestionsCount;
    private int rightAnswerCount;

    private long maxTime;
    private long timeUsed;


    public Quiz() {
    }


    public class SerializedList<E> extends ArrayList<E> implements Serializable {
    }
}
