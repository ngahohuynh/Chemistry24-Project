package com.example.user.chemistry24.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.chemistry24.R;
import com.example.user.chemistry24.models.Question;

import java.util.ArrayList;

public class CheckTestListAdapter extends BaseAdapter {

    ArrayList list;
    LayoutInflater inflater;
    Context context;

    public CheckTestListAdapter(ArrayList list, Context context) {
        this.list = list;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Question data = (Question) getItem(position);
        ViewHolder holder;

        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_gridview,null);
            holder.tvAnsNum = (TextView) convertView.findViewById(R.id.tvAnsNum);
            holder.tvAns = (TextView) convertView.findViewById(R.id.tvAns);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        int i = position + 1;
        holder.tvAnsNum.setText(context.getResources().getString(R.string.tv_question_num)+" "+i
                +context.getResources().getString(R.string.colon)+" ");
        holder.tvAns.setText(data.getTempAns());

        return convertView;
    }

    private static class ViewHolder {
        TextView tvAnsNum, tvAns;
    }
}
