package com.example.interiorshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Main");
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ImageButton but_show = findViewById(R.id.show);
        ImageButton but_shop = findViewById(R.id.shop);
        ImageButton but_modify = findViewById(R.id.user_modify);
        but_show.setBackgroundResource(R.drawable.circle_button);
        but_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);
            }
        });
        but_shop.setBackgroundResource(R.drawable.circle_button);
        but_shop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, ShopActivity.class);
                startActivity(intent);
            }
        });
        but_modify.setBackgroundResource(R.drawable.circle_button);
        but_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserInterfaceActivity.class);
                startActivity(intent);
            }
        });
    }
}