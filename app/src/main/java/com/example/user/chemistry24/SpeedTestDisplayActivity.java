package com.example.user.chemistry24;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.chemistry24.adapter.SpeedTestAdapter;
import com.example.user.chemistry24.controllers.Controller;
import com.example.user.chemistry24.models.Question;
import com.example.user.chemistry24.pager.CustomViewPager;

import java.util.ArrayList;

public class SpeedTestDisplayActivity extends FragmentActivity {

    ArrayList<Question> questions;
    private Controller speedTestController;

    private final int NUM_PAGES = SpeedTestAdapter.NUM_PAGES;
    private CustomViewPager mPager;
    private SpeedTestAdapter mPagerAdapter;

    private TextView tvTiming;
    private LinearLayout lyTiming;

    private CountDownTimer countDownTimer;
    private long timeLeftinMilliseconds = 300000; //5 mins
    private boolean timerRunning = true;

    private Button btnTestList;
    private Button btnTestScore;

    public static int checkAnswer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_test_display);

        init();
        getWidgets();
        setWidgets();
        addListener();
    }

    private void init() {

        speedTestController = Controller.getInstance(this);
        speedTestController.open();
        questions = new ArrayList<>();
        questions = speedTestController.getSpeedTestQuestion();
        speedTestController.close();

        mPagerAdapter = new SpeedTestAdapter(getSupportFragmentManager());

        if(timerRunning) startTimer();
    }

    private void getWidgets() {
        btnTestList = findViewById(R.id.btnTestList);
        btnTestScore = findViewById(R.id.btnTestScore);
        tvTiming = findViewById(R.id.tvTiming);
        lyTiming = findViewById(R.id.lyTiming);
        mPager = findViewById(R.id.speedtestpager);
    }

    private void setWidgets() {
        btnTestList.setVisibility(View.GONE);
        mPager.setAdapter(mPagerAdapter);
    }

    private void addListener() {
        btnTestScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(SpeedTestDisplayActivity.this);
                dialog.setContentView(R.layout.dialog_test_result);

                TextView tvResult, tvTimeResult;
                Button btnHome, btnHistory;
                tvResult = dialog.findViewById(R.id.tvResult);
                tvTimeResult = dialog.findViewById(R.id.tvTimeResult);
                btnHome = dialog.findViewById(R.id.btnHome);
                btnHistory = dialog.findViewById(R.id.btnHistory);

                tvResult.setText(getScore()+"/"+NUM_PAGES);
                tvTimeResult.setText(updateTimer());

                btnHome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer = 0;
                        startActivity(new Intent(SpeedTestDisplayActivity.this,HomeActivity.class));
                        SpeedTestDisplayActivity.this.finish();
                    }
                });
                btnHistory.setVisibility(View.GONE);

                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void gotoNextPage() {
        if(mPager.getCurrentItem() != (NUM_PAGES-1)) mPager.setCurrentItem(mPager.getCurrentItem()+1);
        else showResult();
    }

    public void showResult() {
        checkAnswer = 1;
        timeLeftinMilliseconds = 300000 - timeLeftinMilliseconds;
        countDownTimer.cancel();
        mPager.setCurrentItem(0);
        mPager.setPagingEnabled(true);

        btnTestScore.setVisibility(View.VISIBLE);
        lyTiming.setVisibility(View.GONE);
    }

    private int getScore() {
        int score = 0;
        for(int i = 0; i < questions.size(); i++)
            if(questions.get(i).getTempAns()
                    .equals(questions.get(i).getResult()))
                score++;

        return score;
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftinMilliseconds,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftinMilliseconds = millisUntilFinished;
                if(timerRunning) tvTiming.setText(updateTimer());
            }

            @Override
            public void onFinish() {
                showResult();
            }
        }.start();
    }

    public String updateTimer() {
        int minutes = (int)timeLeftinMilliseconds / 60000;
        int seconds = (int)timeLeftinMilliseconds % 60000 / 1000;

        String timeLeftText;

        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if(seconds < 10) timeLeftText += "0";
        timeLeftText += "" + seconds;

        return timeLeftText;
    }

    @Override
    public void onBackPressed() {
        checkAnswer = 0;
        timerRunning = false;
        countDownTimer.cancel();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage(R.string.on_back_dialog);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                timerRunning = true;
                startTimer();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SpeedTestDisplayActivity.super.onBackPressed();
                SpeedTestDisplayActivity.this.finish();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
