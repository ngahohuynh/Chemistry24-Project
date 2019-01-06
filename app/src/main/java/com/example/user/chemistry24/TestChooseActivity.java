package com.example.user.chemistry24;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.user.chemistry24.adapter.TestChooseAdapter;
import com.example.user.chemistry24.controllers.Controller;

import java.util.ArrayList;

public class TestChooseActivity extends AppCompatActivity {

    private ListView lvTestChoose;
    private ArrayList<String> list;
    private Controller controller;
    private TestChooseAdapter testChooseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_choose);

        init();
        getWidgets();
        setWidgets();
        addListener();
    }

    private void init() {
        list = new ArrayList<>();
        controller = Controller.getInstance(this);
        controller.open();
        list = controller.getIdTest();
        controller.close();
        testChooseAdapter = new TestChooseAdapter(this,R.layout.item_list,list);
    }

    private void getWidgets() {
        lvTestChoose = findViewById(R.id.lvTestChoose);
    }

    private void setWidgets() {
        lvTestChoose.setAdapter(testChooseAdapter);
    }

    private void addListener() {

    }
}