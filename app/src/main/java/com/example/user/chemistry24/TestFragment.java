package com.example.user.chemistry24;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.user.chemistry24.models.Question;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {

    ArrayList<Question> questions;
    public static final String PAGE = "page";
    public static final String CHECK = "CheckAnswer";
    public int mPageNum;
    public int checkAnswer;

    TextView tvFragQuestionNum, tvFragQuestion;
    RadioGroup radioGroup;
    RadioButton radA, radB, radC, radD;

    public TestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_test,container,false);

        tvFragQuestionNum = rootView.findViewById(R.id.tvFragQuestionNum);
        tvFragQuestion = rootView.findViewById(R.id.tvFragQuestion);
        radA = rootView.findViewById(R.id.radA);
        radB = rootView.findViewById(R.id.radB);
        radC = rootView.findViewById(R.id.radC);
        radD = rootView.findViewById(R.id.radD);
        radioGroup = rootView.findViewById(R.id.radioGroup);

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        questions = new ArrayList<>();
        TestDisplayActivity testDisplayActivity = (TestDisplayActivity) getActivity();
        questions = testDisplayActivity.getQuestions();

        mPageNum = getArguments().getInt(PAGE);
        checkAnswer = getArguments().getInt(CHECK);
    }

    public static TestFragment create(int pageNum,int checkAnswer) {
        TestFragment fragment = new TestFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PAGE,pageNum);
        bundle.putInt(CHECK,checkAnswer);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Question item = questions.get(mPageNum);

        tvFragQuestionNum.setText(getString(R.string.tv_question_num)+ " " +(mPageNum+1));
        tvFragQuestion.setText(item.getQuestion());
        radA.setText(item.getChoice_a());
        radB.setText(item.getChoice_b());
        radC.setText(item.getChoice_c());
        radD.setText(item.getChoice_d());

        if(checkAnswer != 0) {
            radA.setClickable(false);
            radB.setClickable(false);
            radC.setClickable(false);
            radD.setClickable(false);
            getCorrectAnswer(getItem(mPageNum).getResult());
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                item.idChoice = checkedId;
                item.setTempAns(getChoiceFromId(checkedId));
            }
        });
    }

    private Question getItem(int position) {
        return questions.get(position);
    }

    private String getChoiceFromId(int id) {
        if(id == R.id.radA) return "A";
        if(id == R.id.radB) return "B";
        if(id == R.id.radC) return "C";
        if(id == R.id.radD) return "D";

        return "";
    }

    private void getCorrectAnswer(String result) {

        if(getItem(mPageNum).getTempAns()
                .equals(result)) {
            if(result.equals("A")) setRadioButtonStyle(radA,true);
            if(result.equals("B")) setRadioButtonStyle(radB,true);
            if(result.equals("C")) setRadioButtonStyle(radC,true);
            if(result.equals("D")) setRadioButtonStyle(radD,true);

            return;
        }

        if(result.equals("A")) setRadioButtonStyle(radA,false);
        else if(result.equals("B")) setRadioButtonStyle(radB,false);
        else if(result.equals("C")) setRadioButtonStyle(radC,false);
        else if(result.equals("D")) setRadioButtonStyle(radD,false);
    }

    private void setRadioButtonStyle(RadioButton rad,boolean check) {
        rad.setTypeface(rad.getTypeface(),Typeface.BOLD);
        if(check)
            rad.setTextColor(ContextCompat.getColor(getContext(),R.color.answer_color));
        else
            rad.setTextColor(Color.RED);
    }

}
