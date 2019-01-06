package com.example.user.chemistry24;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TopicChooseActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnTopic1, btnTopic2, btnTopic3, btnTopic4, btnTopic5;
    private Button btnTheory1, btnTheory2, btnTheory3, btnTheory4, btnTheory5;
    private Button btnExercise1, btnExercise2, btnExercise3, btnExercise4, btnExercise5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_choose);

        init();
        getWidgets();
        setWidgets();
        addListener();
    }

    private void init() {

    }

    private void getWidgets() {
        btnTopic1 = findViewById(R.id.btnTopic1);
        btnTopic2 = findViewById(R.id.btnTopic2);
        btnTopic3 = findViewById(R.id.btnTopic3);
        btnTopic4 = findViewById(R.id.btnTopic4);
        btnTopic5 = findViewById(R.id.btnTopic5);
        btnTheory1 = findViewById(R.id.btnTheory1);
        btnTheory2 = findViewById(R.id.btnTheory2);
        btnTheory3 = findViewById(R.id.btnTheory3);
        btnTheory4 = findViewById(R.id.btnTheory4);
        btnTheory5 = findViewById(R.id.btnTheory5);
        btnExercise1 = findViewById(R.id.btnExercise1);
        btnExercise2 = findViewById(R.id.btnExercise2);
        btnExercise3 = findViewById(R.id.btnExercise3);
        btnExercise4 = findViewById(R.id.btnExercise4);
        btnExercise5 = findViewById(R.id.btnExercise5);
    }

    private void setWidgets() {

    }

    private void addListener() {
        btnTopic1.setOnClickListener(this);
        btnTopic2.setOnClickListener(this);
        btnTopic3.setOnClickListener(this);
        btnTopic4.setOnClickListener(this);
        btnTopic5.setOnClickListener(this);
        btnTheory1.setOnClickListener(this);
        btnTheory2.setOnClickListener(this);
        btnTheory3.setOnClickListener(this);
        btnTheory4.setOnClickListener(this);
        btnTheory5.setOnClickListener(this);
        btnExercise1.setOnClickListener(this);
        btnExercise2.setOnClickListener(this);
        btnExercise3.setOnClickListener(this);
        btnExercise4.setOnClickListener(this);
        btnExercise5.setOnClickListener(this);
    }

    private void onClickBtnTopic(View v1,View v2,View v3,View v4,View v5,View v6,View v7,View v8,View v9,View v10) {
        v1.setVisibility(View.VISIBLE);
        v2.setVisibility(View.VISIBLE);
        v3.setVisibility(View.GONE);
        v4.setVisibility(View.GONE);
        v5.setVisibility(View.GONE);
        v6.setVisibility(View.GONE);
        v7.setVisibility(View.GONE);
        v8.setVisibility(View.GONE);
        v9.setVisibility(View.GONE);
        v10.setVisibility(View.GONE);
    }

    private void onClickBtnTheory(int id) {
        Intent intent = new Intent(TopicChooseActivity.this,TopicTheoryActivity.class);
        intent.putExtra("theory_id",id);
        startActivity(intent);
    }

    private void onClickBtnExercise(int id) {
        Intent intent = new Intent(TopicChooseActivity.this,TopicExerciseActivity.class);
        intent.putExtra("exercise_id",id);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTopic1:
                onClickBtnTopic(btnTheory1,btnExercise1,
                        btnTheory2,btnTheory3,btnTheory4,btnTheory5,btnExercise2,btnExercise3,btnExercise4,btnExercise5);
                break;
            case R.id.btnTopic2:
                onClickBtnTopic(btnTheory2,btnExercise2,
                        btnTheory1,btnTheory3,btnTheory4,btnTheory5,btnExercise1,btnExercise3,btnExercise4,btnExercise5);
                break;
            case R.id.btnTopic3:
                onClickBtnTopic(btnTheory3,btnExercise3,
                        btnTheory1,btnTheory2,btnTheory4,btnTheory5,btnExercise1,btnExercise2,btnExercise4,btnExercise5);
                break;
            case R.id.btnTopic4:
                onClickBtnTopic(btnTheory4,btnExercise4,
                        btnTheory1,btnTheory2,btnTheory3,btnTheory5,btnExercise1,btnExercise2,btnExercise3,btnExercise5);
                break;
            case R.id.btnTopic5:
                onClickBtnTopic(btnTheory5,btnExercise5,
                        btnTheory1,btnTheory2,btnTheory3,btnTheory4,btnExercise1,btnExercise2,btnExercise3,btnExercise4);
                break;
            case R.id.btnTheory1:
                onClickBtnTheory(1);
                break;
            case R.id.btnTheory2:
                onClickBtnTheory(2);
                break;
            case R.id.btnTheory3:
                onClickBtnTheory(3);
                break;
            case R.id.btnTheory4:
                onClickBtnTheory(4);
                break;
            case R.id.btnTheory5:
                onClickBtnTheory(5);
                break;
            case R.id.btnExercise1:
                onClickBtnExercise(1);
                break;
            case R.id.btnExercise2:
                onClickBtnExercise(2);
                break;
            case R.id.btnExercise3:
                onClickBtnExercise(3);
                break;
            case R.id.btnExercise4:
                onClickBtnExercise(4);
                break;
            case R.id.btnExercise5:
                onClickBtnExercise(5);
                break;
        }
    }
}