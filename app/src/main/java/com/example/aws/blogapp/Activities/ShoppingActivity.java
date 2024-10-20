package com.example.aws.blogapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.aws.blogapp.Adapters.ShoppingAdapter;
import com.example.aws.blogapp.Models.Shopping;
import com.example.aws.blogapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ShoppingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ShoppingAdapter mAdapter;


    Shopping shopping;
    ArrayList<Shopping> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        recyclerView=findViewById(R.id.ShoppingRV);

        parser();

        layoutManager = new LinearLayoutManager(ShoppingActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ShoppingAdapter(ShoppingActivity.this, data);


        recyclerView.setAdapter(mAdapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ShoppingActivity.this, Home.class); //지금 액티비티에서 다른 액티비티로 이동하는 인텐트 설정
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //인텐트 플래그 설정
        startActivity(intent);  //인텐트 이동
        finish();   //현재 액티비티 종료
    }


    private void parser() {
        InputStream inputStream = getResources().openRawResource(R.raw.product);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        StringBuffer stringBuffer = new StringBuffer();
        String line = null;

        try {
            while ((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line);
            }
            Log.v("TAG", "StringBuffer : "+ stringBuffer.toString()) ;

            JSONObject jsonObject = new JSONObject(stringBuffer.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("product");

            for(int i = 0; i < jsonArray.length(); i++){

                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                String productImg = jsonObject1.getString("img");
                String productId = jsonObject1.getString("title");
                String price = jsonObject1.getString("lprice");
                String link = jsonObject1.getString("link");


                shopping = new Shopping(productId,price,productImg,link);
                data.add(shopping);

            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(inputStream != null) inputStream.close();
                if(inputStreamReader !=null) inputStreamReader.close();
                if(bufferedReader != null) bufferedReader.close();

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }


}

