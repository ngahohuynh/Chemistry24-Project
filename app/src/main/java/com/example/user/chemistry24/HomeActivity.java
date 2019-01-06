package com.example.user.chemistry24;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        String language = sp.getString("KEY_LANGUAGE","vi");
        setLanguage(language);

        setContentView(R.layout.activity_home);

        init();
        getWidgets();
        setWidgets();
        addListener();
    }

    private void  init() {

    }

    private void getWidgets() {

    }

    private void setWidgets() {

    }

    private void addListener() {

    }

    public void goToTopic(View view) {
        Intent intent = new Intent(HomeActivity.this,TopicChooseActivity.class);
        startActivity(intent);
    }

    public void goToTest(View view) {
        Intent intent = new Intent(HomeActivity.this,TestChooseActivity.class);
        startActivity(intent);
    }

    public void goToSpeedTest(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage(R.string.speed_test_dialog);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(HomeActivity.this,SpeedTestDisplayActivity.class);
                startActivity(intent);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void showSelectLanguage(View view) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.language)
                .setItems(R.array.languages, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String language = "vi";
                        if(which == 0) language ="en";

                        setLanguage(language);

                        //save
                        SharedPreferences sp = getPreferences(MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("KEY_LANGUAGE",language);
                        editor.commit();

                        //refresh UI
                        setContentView(R.layout.activity_home);
                    }


                }).create().show();
    }

    private void setLanguage(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = getResources();
        Configuration con = resources.getConfiguration();
        con.locale = locale;
        resources.updateConfiguration(con,resources.getDisplayMetrics());
    }

    public void showInfo(View view) {
        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.setContentView(R.layout.dialog_info);

        Button btnInfoClose;
        btnInfoClose = dialog.findViewById(R.id.btnInfoClose);

        btnInfoClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void sendFeedback(View view) {
        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.setContentView(R.layout.dialog_feed_back);
        Button btnFeedBackClose,btnSend;
        btnFeedBackClose = dialog.findViewById(R.id.btnFeedBackClose);
        btnSend = dialog.findViewById(R.id.btnSend);
        btnFeedBackClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etFeedBack = dialog.findViewById(R.id.etFeedBack);
                String content = etFeedBack.getText().toString();
                String[] recipient = new String[]{getString(R.string.recipient)};
                String subject = getString(R.string.subject);
                String send = getString(R.string.send_fb);
                if(content.trim().isEmpty()){
                    Toast.makeText(HomeActivity.this,R.string.error,Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setData(Uri.parse("mailto:"));
                    intent.putExtra(Intent.EXTRA_EMAIL, recipient);
                    intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                    intent.putExtra(Intent.EXTRA_TEXT, content);

                    intent.setType("text/plain");
                    startActivity(Intent.createChooser(intent,send));
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
