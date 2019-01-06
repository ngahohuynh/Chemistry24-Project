package com.example.user.chemistry24;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.chemistry24.adapter.CheckTestListAdapter;
import com.example.user.chemistry24.adapter.TestAdapter;
import com.example.user.chemistry24.controllers.Controller;
import com.example.user.chemistry24.models.Question;

import java.util.ArrayList;
import java.util.Map;

public class TestDisplayActivity extends FragmentActivity {

    private Controller testController;
    ArrayList<Question> questions;
    int idTest;
    private final int TYPE = 2;

    private final int NUM_PAGES = TestAdapter.NUM_PAGES;
    private ViewPager mPager;
    private TestAdapter mPagerAdapter;

    private TextView tvTiming;
    private CountDownTimer countDownTimer;
    private long timeLeftinMilliseconds = 3000000; //50 mins
    private boolean timerRunning = true;

    private Button btnTestList, btnTestScore;
    private LinearLayout lyTiming;
    public static int checkAnswer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_display);

        Toast.makeText(this,R.string.test_toast,Toast.LENGTH_LONG).show();

        init();
        getWidgets();
        setWidgets();
        addListener();
    }

    private void init() {
        //Database
        testController = Controller.getInstance(this);
        testController.open();
        questions = new ArrayList<>();
        idTest = getIntent().getIntExtra("idTest",0);
        questions = testController.getTestQuestion(idTest);
        testController.close();
        //slide
        mPagerAdapter = new TestAdapter(getSupportFragmentManager());
        //countdown clock
        if(timerRunning) {
            startTimer();
        }
    }

    private void getWidgets() {
        //Show list of answers
        btnTestList = findViewById(R.id.btnTestList);
        btnTestScore = findViewById(R.id.btnTestScore);
        lyTiming = findViewById(R.id.lyTiming);
        //countdown clock
        tvTiming = findViewById(R.id.tvTiming);
        //slide
        mPager = (ViewPager) findViewById(R.id.testpager);
    }

    private void setWidgets() {
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new DepthPageTransformer());
    }

    private void addListener() {
        btnTestList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerList();
            }
        });

        btnTestScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(TestDisplayActivity.this);
                dialog.setContentView(R.layout.dialog_test_result);

                TextView tvResult, tvTimeResult;
                Button btnHome, btnHistory;
                final ListView lvHistory;
                tvResult = dialog.findViewById(R.id.tvResult);
                tvTimeResult = dialog.findViewById(R.id.tvTimeResult);
                btnHome = dialog.findViewById(R.id.btnHome);
                btnHistory = dialog.findViewById(R.id.btnHistory);
                lvHistory = dialog.findViewById(R.id.lvHistory);

                tvResult.setText(getScore()+"/"+NUM_PAGES);
                tvTimeResult.setText(updateTimer());

                ArrayList<Map<String,String>> list = testController.getHistory(TYPE,idTest);
                SimpleAdapter simpleAdapter = new SimpleAdapter(TestDisplayActivity.this,list,
                        android.R.layout.simple_list_item_2,new String[]{"result","date"},
                        new int[]{android.R.id.text1,android.R.id.text2});
                lvHistory.setAdapter(simpleAdapter);

                btnHistory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lvHistory.setVisibility(View.VISIBLE);
                    }
                });

                btnHome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer = 0;
                        startActivity(new Intent(TestDisplayActivity.this,HomeActivity.class));
                        TestDisplayActivity.this.finish();
                    }
                });

                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
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
                countDownTimer.cancel();
                showResult();
                dialog.dismiss();
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void showResult() {
        checkAnswer = 1;
        timeLeftinMilliseconds = 3000000 - timeLeftinMilliseconds;
        if(mPager.getCurrentItem() > 2) mPager.setCurrentItem(0);
        else mPager.setCurrentItem(5);

        String result = getScore() + "/" + NUM_PAGES;
        testController.insertResult(result,TYPE,idTest);

        btnTestScore.setVisibility(View.VISIBLE);
        btnTestList.setVisibility(View.GONE);
        lyTiming.setVisibility(View.GONE);
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftinMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftinMilliseconds = millisUntilFinished;
                tvTiming.setText(updateTimer());
            }

            @Override
            public void onFinish() {
                showResult();
            }
        }.start();

        timerRunning = true;
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
        if(checkAnswer == 1) {
            checkAnswer = 0;
            startActivity(new Intent(TestDisplayActivity.this,HomeActivity.class));
            return;
        }

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
                TestDisplayActivity.super.onBackPressed();
                TestDisplayActivity.this.finish();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1f);
                view.setTranslationX(0f);
                view.setScaleX(1f);
                view.setScaleY(1f);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }
}
