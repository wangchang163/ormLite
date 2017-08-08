package com.example.ormlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.tv_add)
    TextView tvAdd;
    @InjectView(R.id.tv_del)
    TextView tvDel;
    @InjectView(R.id.tv_change)
    TextView tvChange;
    @InjectView(R.id.tv_search)
    TextView tvSearch;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    @InjectView(R.id.et_search)
    EditText etSearch;
    @InjectView(R.id.tv_search_all)
    TextView tvSearchAll;
    private OrmLiteAdapter adapter;
    private PeopleDao peopleDao;
    private ArrayList<People> list = new ArrayList<>();
    private int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        adapter = new OrmLiteAdapter();
        peopleDao = new PeopleDao(this);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerview.setAdapter(adapter);
        getData();

    }

    @OnClick({R.id.tv_add, R.id.tv_del, R.id.tv_change, R.id.tv_search, R.id.tv_search_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                index++;
                People people = new People("张三" + index, "男", index + "岁","aa");
                peopleDao.add(people);
                getData();
                break;
            case R.id.tv_del:
                People people2=list.get(list.size()-1);
                peopleDao.delete(people2.getId());
                getData();
                break;
            case R.id.tv_change:
                People people3=list.get(list.size()-1);
                people3.setName("李四");
                people3.setSex("女");
                peopleDao.update(people3);
                getData();
                break;
            case R.id.tv_search:
                String str = etSearch.getText().toString().trim();
                if (!TextUtils.isEmpty(str)) {
                    int id = Integer.parseInt(str);
                    People people1 = peopleDao.query(id);
                    adapter.queryData(people1);
                } else {
                    Toast.makeText(this, "请输入学生id", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.tv_search_all:
                getData();
                break;
        }
    }

    private void getData() {
        try {
            list = peopleDao.queryAll();
            adapter.setData(list);
            if (list.size() > 1) {
                recyclerview.smoothScrollToPosition(list.size() - 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
