package com.example.ormlite;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2017/8/8.
 */

public class PeopleDao {

    private Context context;
    private Dao<People, Integer> peopleStringDao;//Integer根据学生id类型判断

    public PeopleDao(Context context) {
        this.context = context;
        peopleStringDao = DatabaseHelper.getHelper(context).getPeopleDao(People.class);
    }

    /**
     * 增加
     *
     * @param people
     */
    public void add(People people) {
        try {
            peopleStringDao.create(people);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除
     * @param id
     */
    public void delete(int id) {
        try {
            peopleStringDao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 查询
     * @param id
     */
    public People query(int id){
        try {
            People people=peopleStringDao.queryForId(id);
            return people;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 查询所有
     * @return
     */
    public ArrayList<People> queryAll() throws SQLException {
        return TransactionManager.callInTransaction(DatabaseHelper.getHelper(context).getConnectionSource(), new Callable<ArrayList<People>>() {
            @Override
            public ArrayList<People> call() throws Exception {
                ArrayList<People> list= (ArrayList<People>) peopleStringDao.queryForAll();
                return list;
            }
        });
    }

    /**
     * 修改
     * @param people
     */
    public void update(People people){
        try {
            peopleStringDao.update(people);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
