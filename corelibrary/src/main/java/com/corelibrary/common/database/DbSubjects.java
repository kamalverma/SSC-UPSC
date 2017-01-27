package com.corelibrary.common.database;

import com.corelibrary.models.Subject;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by kamalverma on 27/01/17.
 */

public class DbSubjects {

    Dao<Subject, Integer> SubjectDao;

    public DbSubjects(DatabaseHelper db) {
        try {
            SubjectDao = db.getSubjectDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int create(Subject Subject) {
        try {
            if (getByCatId(Subject.getCatId()) == null) {
                return SubjectDao.create(Subject);
            } else {
                SubjectDao.update(Subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(Subject Subject) {
        try {
            return SubjectDao.update(Subject);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(Subject Subject) {
        try {
            return SubjectDao.delete(Subject);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Subject getByCatId(int catId) {
        try {
            QueryBuilder<Subject, Integer> qb = SubjectDao.queryBuilder();

            qb.where().eq("catId", catId);

            PreparedQuery<Subject> pq = qb.prepare();
            return SubjectDao.queryForFirst(pq);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Subject> getAll() {
        try {
            return SubjectDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
