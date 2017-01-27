package com.corelibrary.common.database;

import com.corelibrary.models.Question;
import com.corelibrary.models.Subject;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by kamalverma on 27/01/17.
 */

public class DbQuestions {

    Dao<Question, Integer> mQuestionDao;

    public DbQuestions(DatabaseHelper db) {
        try {
            mQuestionDao = db.getQuestiontDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int create(Question question) {
        try {
            if (getByCatId(question.getQuestionId()) == null) {
                return mQuestionDao.create(question);
            } else {
                mQuestionDao.update(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(Question question) {
        try {
            return mQuestionDao.update(question);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(Question question) {
        try {
            return mQuestionDao.delete(question);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Question getByCatId(int questionId) {
        try {
            QueryBuilder<Question, Integer> qb = mQuestionDao.queryBuilder();

            qb.where().eq("questionId", questionId);

            PreparedQuery<Question> pq = qb.prepare();
            return mQuestionDao.queryForFirst(pq);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Question> getAllBySubject(int catId) {
        try {

            QueryBuilder<Question, Integer> qb = mQuestionDao.queryBuilder();
            qb.where().eq("catId", catId);
            PreparedQuery<Question> pq = qb.prepare();
            return mQuestionDao.query(pq);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
