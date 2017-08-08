package com.example.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Administrator on 2017/8/8.
 */


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "people.db";
    private static final int DATABASE_VERSION = 5;
    private static DatabaseHelper instance;

    /**
     * 参数说明：
     * context：上下文。
     * databaseName： 数据库名。
     * factory： 游标实例，多数时候设置成NULL。
     * databaseVersion：数据库版本，当数据库版本升高时，会调用onUpgrade（）方法。
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * 创建数据表
     *
     * @param database
     * @param connectionSource
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, People.class);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据库版本更新
     *
     * @param database         数据库名
     * @param connectionSource
     * @param oldVersion       数据库版本
     * @param newVersion       数据库版本
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource,People.class,true);
            onCreate(database,connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 单例获取helper
     * @param context
     * @return
     */
    public static synchronized DatabaseHelper getHelper(Context context){
        if(instance==null){
            synchronized (DatabaseHelper.class){
                if(instance==null){
                    instance=new DatabaseHelper(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void close() {
        super.close();
        DaoManager.clearCache();
    }

    public <D extends Dao<T, ?>, T> D getPeopleDao(Class<T> clazz) {
        try {
            return getDao(clazz);
        } catch (java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }
}
