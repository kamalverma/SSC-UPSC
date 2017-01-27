package com.corelibrary.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by kamalverma on 30/12/16.
 */

@DatabaseTable(tableName = "subjects")
public class Subject implements Serializable {


    //Add a default constructor
    public Subject() {

    }

    @DatabaseField(columnName = "catName")
    private String catName;

    @DatabaseField(columnName = "catId", id = true)
    private int catId;

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }
}
