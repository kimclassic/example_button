package com.example.kim.app_01_button;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;

public class MainActivity extends AppCompatActivity{

 //Toast초기화용
public static Toast mToast;
// back key가 한 번 눌려졌다는 걸 알기위한 flag입니다.
private boolean isFinish = false;
// back key를 한 번 더 누르라는 메시지를 알리기 위한 notification입니다.
private Toast finishToast;

private Button start,stop;
private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //2. Screen on/off 이벤트를 수신할 BroadcastReceiver 클래스를 등록한다.​
        BroadcastReceiver myReceiver = new Broadcast();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(myReceiver, intentFilter);
        Log.d("onCreate()","브로드캐스트리시버 등록됨");

        //Toast초기화용
        mToast = Toast.makeText(this, "", Toast.LENGTH_LONG);

        //Toast종료용
        finishToast= Toast.makeText(getApplicationContext(),"\'뒤로가기\'를 한번 더 눌러 종료하십시오.", Toast.LENGTH_LONG);

        //MediaPlayer재생
        start = (Button) findViewById(R.id.buttonStart);
        stop = (Button) findViewById(R.id.buttonStop);
        //start.setOnClickListener(this);
        //stop.setOnClickListener(this);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 서비스 시작하기
                Log.d("test", "액티비티-서비스 시작버튼클릭");
                Intent intent = new Intent(getApplicationContext(),MyService.class);
                startService(intent);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 서비스 종료하기
                Log.d("test", "액티비티-서비스 종료버튼클릭");
                Intent intent = new Intent(getApplicationContext(),MyService.class);
                stopService(intent);
            }
        });

    }

    //Toast초기화용
    private void makeToast(final String text, final int duration) {
        mToast.cancel();
        mToast.setText(text);
        mToast.setDuration(duration);
        mToast.show();
    }

    //외부페이지이동
    public void onClick01(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.naver.com"));
        startActivity(intent);
    }

    //알럿_01
    public void onClick02(View v){
        Toast.makeText(getApplicationContext(),"버튼 누름",Toast.LENGTH_LONG).show();
    }

    //전화연결
    public void onClick03(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010-1234-5678"));
        startActivity(intent);
    }

    //페이지이동
    public void onClick04(View v){
        Intent intent_01 = new Intent(getApplicationContext(),Main2Activity.class);
        startActivity(intent_01);
    }

    //알럿_02(대화상자알럿AlertDialog.Builder)
    public void onClick05(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("알림!!");
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "확인 버튼이 눌렸습니다",Toast.LENGTH_SHORT).show();
            }
        });
        alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "취소 버튼이 눌렸습니다",Toast.LENGTH_SHORT).show();
            }
        });
        alert.setMessage("안녕하십니까? 알림 예제소스 학습중 입니다");
        alert.show();
    }

    //웹뷰이동
    public void onClick06(View v){
        Intent intent_01 = new Intent(getApplicationContext(),MainActivity_webView.class);
        startActivity(intent_01);
    }

    //리스트뷰이동
    public void onClick07(View v){
        Snackbar.make(v,"onClick07_리스트뷰이동합니다.",Snackbar.LENGTH_SHORT).show();
        Intent intent_01 = new Intent(getApplicationContext(),MainActivity_listView.class);
        startActivity(intent_01);
    }

    //시작시 콘솔창확인 run -> MainActivity
    public static void main(String[] args)
    {
        System.out.println("Hello World!!!!");
    }

    //1. Screen on/off 이벤트를 수신할 BroadcastReceiver 클래스를 생성한다.
    public class Broadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 수신한 액션을 이 onReceive메소드에서 처리하게 됩니다.

            if (Intent.ACTION_SCREEN_ON == intent.getAction()) {
                // 화면 켜짐
                Log.d("onReceive()","스크린 ON");
            }
            if (Intent.ACTION_SCREEN_OFF == intent.getAction()) {
                // 화면 꺼짐
                Log.d("onReceive()","스크린 OFF");
                //화면꺼질때 (Main3Activity)창이동 보여주기
                Intent i = new Intent(context, Main3Activity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }

            if (Intent.ACTION_HEADSET_PLUG== intent.getAction()) {
                //if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                // 이어폰연결&해제
                Log.d("onReceive()","이어폰확인'S");
                int state = intent.getIntExtra("state", -1);
                switch (state) {
                    case 0:
                        Log.d("111", "Headset is unplugged");
                        break;
                    case 1:
                        Log.d("111", "Headset is plugged");
                        break;
                    default:
                        Log.d("111", "I have no idea what the headset state is");
                }
            }
        }
    }

    /*화면종료 코드 시작*/
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch(keyCode) {
            case KeyEvent.KEYCODE_BACK:
                // back key가 한 번도 눌리지 않았다면,
                if(!isFinish) {
                    // back key가 한 번 눌렸다고 설정합니다.
                    isFinish = true;
                    // toast를 띄웁니다.
                    finishToast.show();
                    return true;
                }
        }
        // back key가 연속으로 두 번 눌리면 종료처리합니다.
        return super.onKeyUp(keyCode, event);
    }
    @Override
    // 터치 스크린 이벤트를 처리하기 위해 호출되는 메쏘드입니다.
    // 여기에서 종료 취소를 구현합니다.
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // toast를 닫습니다.
        finishToast.cancel();
        // back key가 한번 눌렸던 적이 없다고 설정합니다.
        isFinish = false;
        return super.dispatchTouchEvent(ev);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // toast를 닫습니다.
        finishToast.cancel();
        // 프로세스를 종료합니다.
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    /*화면종료 코드 종료*/

}
