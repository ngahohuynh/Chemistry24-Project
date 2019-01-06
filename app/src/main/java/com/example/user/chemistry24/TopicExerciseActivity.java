package com.example.user.chemistry24;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.chemistry24.adapter.CheckTestListAdapter;
import com.example.user.chemistry24.controllers.Controller;
import com.example.user.chemistry24.models.Question;

import java.util.ArrayList;

public class TopicExerciseActivity extends AppCompatActivity {

    private Controller excerciseController;
    private ArrayList<Question> questions;

    private static final int NUM_PAGES = 30;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private Button btnTestList;
    private Button btnExerciseSubmit;
    private Button btnTestScore;
    private LinearLayout lyTiming;

    public int checkAnswer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_exercise);

        Toast.makeText(this, R.string.test_toast, Toast.LENGTH_LONG).show();

        init();
        getWidgets();
        setWidgets();
        addListener();
    }

    private void init() {
        Intent intent = getIntent();
        int exercise_id = intent.getIntExtra("exercise_id", 0);

        excerciseController = Controller.getInstance(this);
        excerciseController.open();
        questions = new ArrayList<>();
        questions = excerciseController.getTopicQuestion(exercise_id);
        excerciseController.close();

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
    }

    private void getWidgets() {
        lyTiming = (LinearLayout) findViewById(R.id.lyTiming);
        btnTestList = findViewById(R.id.btnTestList);
        btnExerciseSubmit = findViewById(R.id.btnExerciseSubmit);
        btnTestScore = findViewById(R.id.btnTestScore);
        mPager = (ViewPager) findViewById(R.id.topicpager);
    }

    private void setWidgets() {
        lyTiming.setVisibility(View.GONE);
        btnTestList.setVisibility(View.VISIBLE);
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new TestDisplayActivity.DepthPageTransformer());
    }

    private void addListener() {
        btnExerciseSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResult();
            }
        });

        btnTestList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerList();
            }
        });

        btnTestScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(TopicExerciseActivity.this);
                dialog.setContentView(R.layout.dialog_test_result);

                TextView tvResult, tvTimeResult, tv2;
                Button btnHome, btnHistory;
                tvResult = dialog.findViewById(R.id.tvResult);
                tvTimeResult = dialog.findViewById(R.id.tvTimeResult);
                tv2 = dialog.findViewById(R.id.tv2);
                btnHome = dialog.findViewById(R.id.btnHome);
                btnHistory = dialog.findViewById(R.id.btnHistory);

                tvTimeResult.setVisibility(View.GONE);
                tv2.setVisibility(View.GONE);

                tvResult.setText(getScore()+"/"+NUM_PAGES);

                btnHome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer = 0;
                        startActivity(new Intent(TopicExerciseActivity.this,HomeActivity.class));
                        TopicExerciseActivity.this.finish();
                    }
                });
                btnHistory.setVisibility(View.GONE);

                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
    }

    private void checkAnswerList() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_test_list);
        dialog.setTitle(R.string.answer_list);

        CheckTestListAdapter adapter = new CheckTestListAdapter(questions,this);
        GridView gvTestList = dialog.findViewById(R.id.gvTestList);
        gvTestList.setAdapter(adapter);

        gvTestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPager.setCurrentItem(position);
                dialog.dismiss();
            }
        });

        Button btnTestClose, btnTestSubmit;
        btnTestClose = dialog.findViewById(R.id.btnTestClose);
        btnTestSubmit = dialog.findViewById(R.id.btnTestSubmit);

        btnTestClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnTestSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResult();
                dialog.dismiss();
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private int getScore() {
        int score = 0;
        for(int i = 0; i < questions.size(); i++)
            if(questions.get(i).getTempAns()
                    .equals(questions.get(i).getResult()))
                score++;

        return score;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    @Override
    public void onBackPressed() {
        checkAnswer = 0;
        TopicExerciseActivity.super.onBackPressed();
        TopicExerciseActivity.this.finish();
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TopicFragment.create(position,checkAnswer);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public void showResult() {
        checkAnswer = 1;
        if(mPager.getCurrentItem() > 2) mPager.setCurrentItem(0);
        else mPager.setCurrentItem(5);

        btnTestScore.setVisibility(View.VISIBLE);
        btnExerciseSubmit.setVisibility(View.GONE);
    }

}