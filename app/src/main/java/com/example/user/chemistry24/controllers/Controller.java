package com.example.user.chemistry24.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.user.chemistry24.R;
import com.example.user.chemistry24.database.DBOpenHelper;
import com.example.user.chemistry24.models.Question;
import com.example.user.chemistry24.models.TopicTheory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Controller {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static Controller instance;
    private Context context;

    private Controller(Context context) {
        this.context = context;
        this.openHelper = new DBOpenHelper(context);
    }

    public static Controller getInstance(Context context) {
        if(instance == null)
            instance = new Controller(context);

        return instance;
    }

    public void open() {
        this.database = openHelper.getReadableDatabase();
    }

    public void close() {
        if(database != null) this.database.close();
    }

    public TopicTheory getTopicTheory(int idTopic) {
        Cursor cursor = database.rawQuery("SELECT * FROM TopicTheory WHERE _id = "+idTopic,null);
        cursor.moveToFirst();
        TopicTheory topicTheory = new TopicTheory(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
        cursor.close();
        return topicTheory;
    }

    public ArrayList<Question> getTopicQuestion(int idTopic) {
        ArrayList<Question> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM TopicQuestion WHERE idTopic = "+idTopic,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Question item = new Question(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),
                    cursor.getString(5),cursor.getString(6),cursor.getInt(8),"");
            list.add(item);
            cursor.moveToNext();
        }

        cursor.close();
        return list;
    }

    public ArrayList<Question> getTestQuestion(int idTest) {
        ArrayList<Question> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM TestQuestion WHERE idTest = "+idTest,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Question item = new Question(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),
                    cursor.getString(5),cursor.getString(6),cursor.getInt(7),"");
            list.add(item);
            cursor.moveToNext();
        }

        cursor.close();
        return list;
    }

    public ArrayList<String> getIdTest() {
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT DISTINCT idTest FROM TestQuestion ORDER BY idTest ASC",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String item = context.getString(R.string.test_id) + " " + cursor.getInt(0);
            list.add(item);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public ArrayList<Question> getSpeedTestQuestion() {
        ArrayList<Question> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM TopicQuestion WHERE type = 1 ORDER BY RANDOM() LIMIT 15 ",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Question item = new Question(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),
                    cursor.getString(5),cursor.getString(6),cursor.getInt(8),"");
            list.add(item);
            cursor.moveToNext();
        }

        cursor.close();
        return list;
    }

    public void insertResult(String result, int type, int idTest) {
        database = openHelper.getWritableDatabase();

        Calendar cal = Calendar.getInstance();
        Date d = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = dateFormat.format(d);

        ContentValues contentValues = new ContentValues();
        contentValues.put("result",result);
        contentValues.put("date",date);
        contentValues.put("type",type);
        contentValues.put("idTest",idTest);

        database.insert("History",null,contentValues);
    }

    public ArrayList<Map<String,String>> getHistory(int type, int idTest) {
        ArrayList<Map<String,String>> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM History WHERE type = "+type+" AND idTest = "+idTest,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Map<String,String> item = new HashMap<>();
            item.put("result",cursor.getString(1));
            item.put("date",cursor.getString(2));
            list.add(item);
            cursor.moveToNext();
        }

        cursor.close();
        return list;
    }
}
