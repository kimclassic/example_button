package com.example.kim.app_01_button;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
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

        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        ImageView iv = (ImageView) findViewById(R.id.image_male);
        iv.setOnClickListener(new MyListener());


        TextView textView_id = (TextView)findViewById(R.id.textView_id);
        TextView textView_pw = (TextView)findViewById(R.id.textView_pw);

        Intent intent_01 = getIntent();
        String id = intent_01.getExtras().getString("input_id");
        String pw = intent_01.getExtras().getString("input_pw");

        textView_id.setText(String.valueOf(id));
        textView_pw.setText(String.valueOf(pw));

    }

    class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Log.d("onReceive()","이미지선택");
            //여기서는 왜 Toast.makeText(this,로 선언이 안될까? 밑은 가능함
            //Toast.makeText(getApplicationContext(),"꼭꼭 숨었다가 웃으면 나타나\n어디서 온 걸까 거짓말하지마\n천사가 맞잖아 니 정체가 뭐야",Toast.LENGTH_LONG).show();

            Toast toast_03= Toast.makeText(getApplicationContext(),"꼭꼭 숨었다가 웃으면 나타나\n어디서 온 걸까 거짓말하지마\n천사가 맞잖아 니 정체가 뭐야",Toast.LENGTH_LONG);
            toast_03.setGravity(Gravity.CENTER,50,50);
            toast_03.show();
        }
    }

    public void onClick_back(View v){
        finish();
    }

    /*
    ImageView에 이미지 넣기
    image.setImageResource(R.id.이미지 이름);
    이미지 이름은 res/drawable에 저장된 이미지 파일명입니다.
    ImageView에서 이미지 제거
    imageVeiw.setImageResource(0);
    */

}
