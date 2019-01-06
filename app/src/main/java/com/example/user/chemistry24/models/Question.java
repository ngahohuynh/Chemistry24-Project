package com.example.user.chemistry24.models;

import java.io.Serializable;

public class Question implements Serializable {

        private int _idQuestion;
        private String question;
        private String choice_a;
        private String choice_b;
        private String choice_c;
        private String choice_d;
        private String result;
        private int idTest;
        private String temp_ans = "";
        public int idChoice = -1;   //used for check id of radio button

        public Question(int _idQuestion, String question, String choice_a, String choice_b, String choice_c, String choice_d, String result, int idTest, String temp_ans) {
            this._idQuestion = _idQuestion;
            this.question = question;
            this.choice_a = choice_a;
            this.choice_b = choice_b;
            this.choice_c = choice_c;
            this.choice_d = choice_d;
            this.result = result;
            this.idTest = idTest;
            this.temp_ans = temp_ans;
        }

    public int get_idQuestion() {
        return _idQuestion;
    }

    public void set_idQuestion(int _idQuestion) {
        this._idQuestion = _idQuestion;
    }

    public String getQuestion() {
            return question;
        }

    public void setQuestion(String question) {
            this.question = question;
        }

        public String getChoice_a() {
            return choice_a;
        }

        public void setChoice_a(String choice_a) {
            this.choice_a = choice_a;
        }

        public String getChoice_b() {
            return choice_b;
        }

        public void setChoice_b(String choice_b) {
            this.choice_b = choice_b;
        }

        public String getChoice_c() {
            return choice_c;
        }

        public void setChoice_c(String choice_c) {
            this.choice_c = choice_c;
        }

        public String getChoice_d() {
            return choice_d;
        }

        public void setChoice_d(String choice_d) {
            this.choice_d = choice_d;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public int getIdTest() {
            return idTest;
        }

        public void setIdTest(int idTest) {
            this.idTest = idTest;
        }

        public String getTempAns() {
            return temp_ans;
        }

        public void setTempAns(String temp_ans) {
            this.temp_ans = temp_ans;
        }
}

