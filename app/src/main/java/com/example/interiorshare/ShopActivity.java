package com.example.interiorshare;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.interiorshare.Adapter.ShoppingAdapter;

public class ShopActivity extends AppCompatActivity {
    ImageButton IKEA;
    ListView shop;
    String[] data= {"이케아","한샘"};
    String[] url = {"https://www.ikea.com/","http://mall.hanssem.com/"};
    int[] image={R.drawable.ikea, R.drawable.hanssem};
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);
        shop=findViewById(R.id.list);
        this.setTitle("Shop");
        ShoppingAdapter shoppingAdapter = new ShoppingAdapter(this,data,url,image);
        shop.setAdapter(shoppingAdapter);

//        IKEA = findViewById(R.id.IKEA);
//        IKEA.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ikea.com/"));
//                startActivity(urlintent);
//            }
//        });
    }
}
