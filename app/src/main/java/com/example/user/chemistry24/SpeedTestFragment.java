package com.example.user.chemistry24;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.chemistry24.models.Question;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SpeedTestFragment extends Fragment implements View.OnClickListener{

    ArrayList<Question> questions;
    SpeedTestDisplayActivity speedTestDisplayActivity;

    TextView tvSpeedTestQuestion;
    Button btnChoiceA, btnChoiceB, btnChoiceC, btnChoiceD;

    public int mPageNum;
    public static final String PAGE = "page";
    public int checkAnswer;
    public static final String CHECK = "CheckAnswer";

    public SpeedTestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_speed_test,container,false);

        tvSpeedTestQuestion = rootView.findViewById(R.id.tvSpeedTestQuestion);
        btnChoiceA = rootView.findViewById(R.id.btnChoiceA);
        btnChoiceB = rootView.findViewById(R.id.btnChoiceB);
        btnChoiceC = rootView.findViewById(R.id.btnChoiceC);
        btnChoiceD = rootView.findViewById(R.id.btnChoiceD);

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        questions = new ArrayList<>();
        speedTestDisplayActivity = (SpeedTestDisplayActivity) getActivity();
        questions = speedTestDisplayActivity.getQuestions();

        mPageNum = getArguments().getInt(PAGE);
        checkAnswer = getArguments().getInt(CHECK);
    }

    public static SpeedTestFragment create(int pageNum,int checkAnswer) {
        SpeedTestFragment fragment = new SpeedTestFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PAGE,pageNum);
        bundle.putInt(CHECK,checkAnswer);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnChoiceA.setOnClickListener(this);
        btnChoiceB.setOnClickListener(this);
        btnChoiceC.setOnClickListener(this);
        btnChoiceD.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Question item = questions.get(mPageNum);

        tvSpeedTestQuestion.setText(getString(R.string.tv_question_num)+ " " + (mPageNum + 1) + getString(R.string.colon) + " " + item.getQuestion());
        btnChoiceA.setText(item.getChoice_a());
        btnChoiceB.setText(item.getChoice_b());
        btnChoiceC.setText(item.getChoice_c());
        btnChoiceD.setText(item.getChoice_d());

        if (checkAnswer != 0) {
            btnChoiceA.setClickable(false);
            btnChoiceB.setClickable(false);
            btnChoiceC.setClickable(false);
            btnChoiceD.setClickable(false);
            getCorrectAnswer(questions.get(mPageNum).getResult());
        }
    }

    @Override
    public void onClick(View v) {
        final Question item = questions.get(mPageNum);
        item.setTempAns(getChoiceFromId(v.getId()));
        v.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorPrimaryDark));

        speedTestDisplayActivity.gotoNextPage();
    }

    private String getChoiceFromId(int id) {
        if(id == R.id.btnChoiceA) return "A";
        if(id == R.id.btnChoiceB) return "B";
        if(id == R.id.btnChoiceC) return "C";
        if(id == R.id.btnChoiceD) return "D";

        return "";
    }

    private void getCorrectAnswer(String result) {

        if(questions.get(mPageNum).getTempAns()
                .equals(result)) {
            if(result.equals("A")) setButtonStyle(btnChoiceA,true);
            if(result.equals("B")) setButtonStyle(btnChoiceB,true);
            if(result.equals("C")) setButtonStyle(btnChoiceC,true);
            if(result.equals("D")) setButtonStyle(btnChoiceD,true);

            return;
        }

        if(result.equals("A")) setButtonStyle(btnChoiceA,false);
        else if(result.equals("B")) setButtonStyle(btnChoiceB,false);
        else if(result.equals("C")) setButtonStyle(btnChoiceC,false);
        else if(result.equals("D")) setButtonStyle(btnChoiceD,false);
    }

    private void setButtonStyle(Button button,boolean check) {
        if(check) button.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.answer_color));
        else  button.setBackgroundColor(Color.RED);
    }
}
