package com.example.interiorshare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.interiorshare.Adapter.ListViewAdapter;
import com.example.interiorshare.Adapter.ListViewItem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ListActivity extends AppCompatActivity {

    ListView listview;
    ListViewAdapter adapter;
    Button but_story, but_shop;
    String[] SavedFiles;
    private static final String TAG = "AnonymousAuth";
    ImageView photo;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("공유하기");
        setContentView(R.layout.list);
        but_story = findViewById(R.id.story);
        but_story.setBackgroundColor(getResources().getColor(R.color.sky));
        but_story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, WriteActivity.class);
                startActivity(intent);
            }
        });
        showSavedFiles();
    }
    void showSavedFiles() {
        SavedFiles = ListActivity.this.fileList();
        //getApplicationContext는 application context를 가르킴
        //this는 activity context를 가르킴
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, SavedFiles);
        //왜 이걸 사용하면 리스트가 뜰까?
        //리스트뷰와 데이터를 중간에서 연결 및 관리
        //데이터에게 "뭘 보여줄꺼? 줘봐" - 리스트에게 "데이터줄테니까 보여줘"
//        adapter = new ListViewAdapter();
        listview = findViewById(R.id.listView);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(ListActivity.this, ContentActivity.class);
                ListViewItem item = new ListViewItem();
                item.setTitle((String) adapterView.getAdapter().getItem(position));
                item.setContent((String) adapterView.getAdapter().getItem(position));
                item.setDetail((String) adapterView.getAdapter().getItem(position));

                String fileName = (String) adapterView.getAdapter().getItem(position);
                FileInputStream fis;
                try {
                    fis = getApplicationContext().openFileInput(fileName);

                    ObjectInputStream ois = new ObjectInputStream(fis);

                    String[] data = (String[]) ois.readObject();
                    Log.d(TAG, "onItemClick: title: " + data[1]);
                    Log.d(TAG, "onItemClick: content: " + data[0]);
                    Log.d(TAG, "onItemClick: detail: " + data[2]);
                    intent.putExtra("title", data[1]);
                    intent.putExtra("content", data[0]);
                    intent.putExtra("detail", data[2]);
                    intent.putExtra("date",data[3]);
                    Log.d(TAG, "image");
                    startActivity(intent);
                } catch (FileNotFoundException e) {
                    Log.d(TAG, "ㅁ");
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.d(TAG, "ㄴ");
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    Log.d(TAG, "ㄷ");
                    e.printStackTrace();
                }
            }
        });
    }
}
