package com.example.interiorshare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.interiorshare.Adapter.ListViewAdapter;

import java.util.ArrayList;

public class ContentActivity extends AppCompatActivity {

    final private String TAG = getClass().getSimpleName();
    ArrayList<String> ListViewItem = new ArrayList<String>();
    // 사용할 컴포넌트 선언
    TextView title, content, date, detail;
    LinearLayout content_layout;
    EditText content_et;
    Button reg_button, delete_button;
    ImageView photo;
    Uri uri;
    String board_seq = "";
    /*String userid = "";*/
    String imgName;
    private static final String POST = "post";
    private ListView listView;
    private ListViewAdapter listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);
        listView = findViewById(R.id.listView);
        title = findViewById(R.id.content_title);
        content = findViewById(R.id.content_content);
        date = findViewById(R.id.content_date);
        content_layout = findViewById(R.id.content_layout);
        content_et = findViewById(R.id.content_write);
        reg_button = findViewById(R.id.content_register);
        delete_button = findViewById(R.id.add);
        photo=findViewById(R.id.content_photo);
        detail =findViewById(R.id.content_detail);
        String Title = (String) getIntent().getExtras().getSerializable("title"); //Object 받기
        String Content = (String) getIntent().getExtras().getSerializable("content"); //Object 받기
        String Detail = (String) getIntent().getExtras().getSerializable("detail"); //Object 받기
        imgName = Title; //이름을 title로 저장
        String Date = (String)getIntent().getExtras().getSerializable("date");
        try{
            String imgf = getCacheDir() +"/" + imgName;
            Bitmap bm = BitmapFactory.decodeFile(imgf);
            photo.setImageBitmap(bm);
            Toast.makeText(getApplicationContext(),"파일로드성공", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(),"파일로드실패", Toast.LENGTH_SHORT).show();
        }

        Log.d(TAG, "Get?");
        title.setText(Title);
        content.setText(Content);
        detail.setText(Detail);
//        date.setText(Date);
        Log.d(TAG, "title");

//        delete_button.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

    public void onClickUrl(View view) {
        try{
            String url = detail.getText().toString();
            Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (Exception e){

        }
    }
}


