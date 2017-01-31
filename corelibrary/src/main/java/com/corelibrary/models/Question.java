package com.corelibrary.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamalverma on 27/01/17.
 */

@DatabaseTable(tableName = "questions")
public class Question implements Serializable {

    @DatabaseField
    private String qnText;

    @DatabaseField
    private String qnType;

    @DatabaseField
    private String tags;
    @DatabaseField
    private String explanation;


    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private String[] opts;


    @SerializedName("ans")
    @DatabaseField
    private int answer;
    @DatabaseField
    private int catId;

    @DatabaseField
    private int questionId;

    @DatabaseField
    private String extLink;

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
    private String cDate; //Date of creation

    @Expose(serialize = false, deserialize = false)
    @DatabaseField
    private String mDate;  //Date of modification/attempted


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


    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
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
        return extLink;
    }

    public void setExternalLink(String externalLink) {
        this.extLink = externalLink;
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
        return cDate;
    }

    public void setC_date(String c_date) {
        this.cDate = c_date;
    }

    public String getM_date() {
        return mDate;
    }

    public void setM_date(String m_date) {
        this.mDate = m_date;
    }


    public String getQnType() {
        return qnType;
    }

    public void setQnType(String qnType) {
        this.qnType = qnType;
    }

    public String[] getOpts() {
        return opts;
    }

    public void setOpts(String[] opts) {
        this.opts = opts;
    }

    public String getExtLink() {
        return extLink;
    }

    public void setExtLink(String extLink) {
        this.extLink = extLink;
    }

    public String getcDate() {
        return cDate;
    }

    public void setcDate(String cDate) {
        this.cDate = cDate;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }


    public class SerializedList<E> extends ArrayList<E> implements Serializable {
    }
}
