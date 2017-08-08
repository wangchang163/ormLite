package com.example.ormlite;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/8.
 */

@DatabaseTable(tableName = "tb_people")
public class People implements Serializable {
    public static final String PEOPLE_ID = "id";
    @DatabaseField(columnName = PEOPLE_ID,generatedId = true)
    public int id;   //用户id
    @DatabaseField(columnName = "name")
    public String name; //用户名
    @DatabaseField(columnName = "sex")
    public String sex;  //用户性别
    @DatabaseField(columnName = "age")
    public String age;  //用户年龄
    @DatabaseField(columnName = "desc")
    public String desc;

    public People(String name, String sex, String age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public People(String name, String sex, String age, String desc) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.desc = desc;
    }


    public People() {
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
