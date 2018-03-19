package com.example.kim.app_01_button;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity_listView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        // listview, header, footer 참조 획득.
        final View header = getLayoutInflater().inflate(R.layout.listview_header, null, false) ;
        final View footer = getLayoutInflater().inflate(R.layout.listview_footer, null, false) ;

        // 빈 데이터 리스트 생성.
        final ArrayList<String> items = new ArrayList<String>() ;
        // 데이터를 지정하지 않은 adapter 생성하여 listview에 지정. simple_list_item_1:선택불가
       final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice,items) ;
        // listview 생성
        final ListView listview = (ListView) findViewById(R.id.listview1) ;
        // listView에 header, footer 추가.
        listview.addHeaderView(header) ;
        listview.addFooterView(footer) ;
        // listview에 adapter 지정
        listview.setAdapter(adapter) ;

        //클릭했을 때 발생하는 리스너
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                int count,num;
                count = adapter.getCount();                 // 총갯수
                num = listview.getCheckedItemPosition();    // 현재 선택된 아이템의 position 획득.
                Snackbar.make(v,"현재선택된아이템="+count+"중 몇번"+num+"(물리적="+id+")",Snackbar.LENGTH_SHORT).show();
            }
        });

        // add button에 대한 이벤트 처리.
        Button addButton = (Button)findViewById(R.id.add) ;
        addButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                int count;
                int item;
                count = adapter.getCount()+1;
                // 아이템 추가.
                items.add("LIST" + Integer.toString(count));
                // listview 갱신
                adapter.notifyDataSetChanged();

               //헤더에 값변경내용추가(실시간체크는아님)
                TextView textView = (TextView) header.findViewById(R.id.count) ;
                textView.setText(Integer.toString(count)) ;
            }
        }) ;

        // modify button에 대한 이벤트 처리.
        Button modifyButton = (Button)findViewById(R.id.modify) ;
        modifyButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                int count, checked ;
                count = adapter.getCount() ;
                if (count > 0) {
                    // 현재 선택된 아이템의 position 획득.
                    checked = listview.getCheckedItemPosition()-1;
                    Log.d("test",count+"현재 선택된 아이템"+checked);
                    if (checked > -1 && checked < count) {
                        // 아이템 수정
                        items.set(checked, Integer.toString(checked+1) + "번 아이템 수정") ;
                        // listview 갱신
                       adapter.notifyDataSetChanged();
                    }
                }
            }
        }) ;

        // delete button에 대한 이벤트 처리.
        Button deleteButton = (Button)findViewById(R.id.delete) ;
        deleteButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                int count, checked ;
                count = adapter.getCount() ;
                if (count > 0) {
                    // 현재 선택된 아이템의 position 획득.
                    checked = listview.getCheckedItemPosition()-1;
                    Log.d("test",count+"현재 선택된 아이템"+checked);
                    if (checked > -1 && checked < count) {
                        // 아이템 삭제
                        items.remove(checked) ;
                        // listview 선택 초기화.
                        listview.clearChoices();
                        // listview 갱신.
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }) ;


    }
}