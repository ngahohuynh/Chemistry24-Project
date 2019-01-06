package com.example.user.chemistry24.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.user.chemistry24.R;
import com.example.user.chemistry24.TestDisplayActivity;

import java.util.ArrayList;

public class TestChooseAdapter extends ArrayAdapter<String> {

    private Context context;
    private int resource;
    private ArrayList<String> list;

    public TestChooseAdapter(Context context, int resource, ArrayList<String> list) {
        super(context,resource,list);
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.btnTestChoose = convertView.findViewById(R.id.btnTestChoose);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String test = list.get(position);
        viewHolder.btnTestChoose.setText(test);

        viewHolder.btnTestChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog(position);
            }
        });

        return convertView;
    }

    public class ViewHolder {
        Button btnTestChoose;
    }

    private void showAlertDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("");
        builder.setMessage(R.string.test_dialog);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                goToTest(position);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void goToTest(int position) {
        Intent intent = new Intent(context,TestDisplayActivity.class);
        intent.putExtra("idTest",position+1);
        context.startActivity(intent);
    }
}
