package com.example.kim.app_01_button;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void onclick_back(View v){
        finish();
        //finish로 하지않으면 브로드캐스트리시버동작이 겹침
        //Intent intent_02 = new Intent(getApplicationContext(),MainActivity.class);
        //startActivity(intent_02);
    }

    public void onClick_toast(View v){
        Toast toast_02 = Toast.makeText(this,"안녕 클레오파트라 \n 세상에서 제일가는 포테이토칩",Toast.LENGTH_LONG);
        toast_02.setGravity(Gravity.CENTER,50,50);
        toast_02.show();
    }

    public void onClick_login(View v){

        String id,pw;

        EditText text_id = (EditText)findViewById(R.id.editText_id);
        EditText text_pw = (EditText)findViewById(R.id.editText_pw);

        id = text_id.getText().toString();
        pw = text_pw.getText().toString();

        // Intent로 다른 Activity실행시킬때
        Intent intent_01 = new Intent(getApplicationContext(),Main3Activity.class);
        //데이터 전달시
        intent_01.putExtra("input_id",id);
        intent_01.putExtra("input_pw",pw);
        startActivity(intent_01);
    }


}
