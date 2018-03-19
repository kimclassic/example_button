package com.example.kim.app_01_button;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity_webView extends AppCompatActivity {
    private WebView mWebView;
    private String mCurrentUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_web_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mCurrentUrl = "http://m0.etoos.com";
        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.setWebViewClient(new WebViewClient());         //새창열기없이웹뷰에서다시열기
        mWebView.setWebViewClient(new WebViewClientClass());    //스크립트사용 선언해도 알럿창이 안뜸 -> setWebChromeClient로변경


        //크롬용브라우저사용시
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result) {
                //다이아로그박스 출력
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Alert title")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok,
                                new AlertDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        result.confirm();
                                    }
                                })
                        .setCancelable(false)
                        .create()
                        .show();
                return true;
            }
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final android.webkit.JsResult result) {
                new AlertDialog.Builder(view.getContext())
                    .setTitle("Confirm title")
                    .setMessage(message)
                    .setPositiveButton("Yes",
                            new AlertDialog.OnClickListener(){
                                public void onClick(DialogInterface dialog, int which) {
                                    result.confirm();
                                }
                            })
                    .setNegativeButton("No",
                            new AlertDialog.OnClickListener(){
                                public void onClick(DialogInterface dialog, int which) {
                                    result.cancel();
                                }
                            })
                    .setCancelable(false)
                    .create()
                    .show();
            return true;
        }
            @Override
            public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, Message resultMsg) {
                Log.d("test", "onCreateWindow-외부 브라우저사용");
                WebView newWebView = new WebView(MainActivity_webView.this);
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(newWebView);
                resultMsg.sendToTarget();
                newWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                        browserIntent.setData(Uri.parse(url));
                        startActivity(browserIntent);
                        return true;
                    }
                });

                return true;
            }
    });

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);                            // javascript를 실행할 수 있도록 설정
        settings.setJavaScriptCanOpenWindowsAutomatically (true);       // javascript가 window.open()을 사용할 수 있도록 설정
        settings.setPluginState(WebSettings.PluginState.ON_DEMAND);    // 플러그인을 사용할 수 있도록 설정
        settings.setBuiltInZoomControls(true);                          // 안드로이드에서 제공하는 줌 아이콘을 사용할 수 있도록 설정
        settings.setSupportMultipleWindows(true);
        mWebView.loadUrl(mCurrentUrl);
        mCurrentUrl=null;
    }

    private  class WebViewClientClass extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view,String url) {
            Log.e("url", url);
            //view.loadUrl(url); 웹뷰종료하고 메인으로돌아감
            //카카오링크인텐트안되서추가함
            if (url.startsWith("intent:")) {
                try {
                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    Intent existPackage = getPackageManager().getLaunchIntentForPackage(intent.getPackage());
                    if (existPackage != null) {
                        startActivity(intent);
                    } else {
                        Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                        marketIntent.setData(Uri.parse("market://details?id=" + intent.getPackage()));
                        startActivity(marketIntent);
                    }
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                view.loadUrl(url);
            }
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d("test","onKeyDown"+keyCode+KeyEvent.KEYCODE_BACK);
        if((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()){
            mWebView.goBack();
            return true;
        }
        //백할 페이가 없다면
        if ((keyCode == KeyEvent.KEYCODE_BACK) && (mWebView.canGoBack() == false)){
            Toast.makeText(this, "버튼 클릭 이벤트", Toast.LENGTH_SHORT).show();

            //다이아로그박스 출력
            new AlertDialog.Builder(this)
                    .setTitle("메인홈으로이동")
                    .setMessage("웹뷰를 종료하시겠습니까?")
                    .setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            android.os.Process.killProcess(android.os.Process.myPid());
                        }
                    })
                    .setNegativeButton("아니오",  null).show();
        }
        return super.onKeyDown(keyCode, event);
    }
}
