package com.example.kim.app_01_button;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by kim on 2017-12-12.
 */

public class SplashActivity  extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //시간조건추가
        try {
            Thread.sleep(4000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
