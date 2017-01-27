package com.corelibrary.common.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.corelibrary.models.Question;
import com.corelibrary.models.Subject;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by kamalverma on 27/01/17.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "prepup.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Subject, Integer> mSubjectDao = null;
    private Dao<Question, Integer> mQuestionDao = null;

    private static DatabaseHelper dbHelper;

    public static void init(Context context) {
        dbHelper = new DatabaseHelper(context);
    }


    public static DatabaseHelper getInstance(Context ctx) {

        if (dbHelper == null) {
            init(ctx);
        }

        return dbHelper;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Subject.class);
            TableUtils.createTable(connectionSource, Question.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Subject.class, true);
            TableUtils.dropTable(connectionSource, Question.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /* Subjects */

    public Dao<Subject, Integer> getSubjectDao() throws SQLException {
        if (mSubjectDao == null) {
            mSubjectDao = getDao(Subject.class);
        }

        return mSubjectDao;
    }

     /* Subjects */

    public Dao<Question, Integer> getQuestiontDao() throws SQLException {
        if (mQuestionDao == null) {
            mQuestionDao = getDao(Question.class);
        }

        return mQuestionDao;
    }

    @Override
    public void close() {
        mSubjectDao = null;

        super.close();
    }
}