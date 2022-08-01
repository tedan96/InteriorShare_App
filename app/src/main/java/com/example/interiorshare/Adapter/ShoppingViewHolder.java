package com.example.interiorshare.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.interiorshare.R;

public class ShoppingViewHolder {
    ImageView logo;
    TextView name;

    ShoppingViewHolder(View v){
        logo=v.findViewById(R.id.logo);
//        name=v.findViewById(R.id.textView11);
    }
}
