package com.corelibrary.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by kamalverma on 27/01/17.
 */

@DatabaseTable(tableName = "questions")
public class Question implements Serializable {

    @SerializedName("qn_text")
    @DatabaseField
    private String qnText;

    @DatabaseField
    private String qnType;

    @DatabaseField
    private String tags;
    @DatabaseField
    private String explanation;
    @DatabaseField
    private String opt1;
    @DatabaseField
    private String opt2;
    @DatabaseField
    private String opt3;
    @DatabaseField
    private String opt4;
    @DatabaseField
    private String opt5;
    @DatabaseField
    private String opt6;

    @SerializedName("ans")
    @DatabaseField
    private String answer;
    @DatabaseField
    private int catId;

    @SerializedName("question_id")
    @DatabaseField
    private int questionId;

    @Expose(serialize = false, deserialize = false)
    @DatabaseField
    private String externalLink;

    @Expose(serialize = false, deserialize = false)
    @DatabaseField
    private boolean attempted;

    @Expose(serialize = false, deserialize = false)
    @DatabaseField
    private boolean bookmarked;

    @Expose(serialize = false, deserialize = false)
    @DatabaseField
    private boolean hidden;

    @Expose(serialize = false, deserialize = false)
    @DatabaseField
    private String c_date; //Date of creation

    @Expose(serialize = false, deserialize = false)
    @DatabaseField
    private String m_date;  //Date of modification/attempted


    public Question() {
    }

    public String getQnText() {

        return qnText;
    }

    public void setQnText(String qnText) {
        this.qnText = qnText;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getOpt1() {
        return opt1;
    }

    public void setOpt1(String opt1) {
        this.opt1 = opt1;
    }

    public String getOpt2() {
        return opt2;
    }

    public void setOpt2(String opt2) {
        this.opt2 = opt2;
    }

    public String getOpt3() {
        return opt3;
    }

    public void setOpt3(String opt3) {
        this.opt3 = opt3;
    }

    public String getOpt4() {
        return opt4;
    }

    public void setOpt4(String opt4) {
        this.opt4 = opt4;
    }

    public String getOpt5() {
        return opt5;
    }

    public void setOpt5(String opt5) {
        this.opt5 = opt5;
    }

    public String getOpt6() {
        return opt6;
    }

    public void setOpt6(String opt6) {
        this.opt6 = opt6;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }


    public String getExternalLink() {
        return externalLink;
    }

    public void setExternalLink(String externalLink) {
        this.externalLink = externalLink;
    }

    public boolean isAttempted() {
        return attempted;
    }

    public void setAttempted(boolean attempted) {
        this.attempted = attempted;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getC_date() {
        return c_date;
    }

    public void setC_date(String c_date) {
        this.c_date = c_date;
    }

    public String getM_date() {
        return m_date;
    }

    public void setM_date(String m_date) {
        this.m_date = m_date;
    }
}
