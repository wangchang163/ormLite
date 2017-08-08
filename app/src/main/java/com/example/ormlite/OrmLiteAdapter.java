package com.example.ormlite;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2017/8/8.
 */

public class OrmLiteAdapter extends RecyclerView.Adapter<OrmLiteAdapter.ViewHolder> {


    private ArrayList<People> data = new ArrayList<>();

    public void setData(ArrayList<People> list) {
        data.clear();
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void queryData(People people) {
        if (people != null) {
            data.clear();
            data.add(people);
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        People people = data.get(position);
        holder.tvName.setText("ID: [" + people.getId() + "]   NAME: [" + people.getName() + "]   SEX: [" + people.getSex() + "]   AGE: [" + people.getAge() + "]");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_name)
        TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

}
