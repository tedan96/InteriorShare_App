package com.example.interiorshare.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.interiorshare.R;

public class ShoppingAdapter extends ArrayAdapter<String> {
    String[] data;
    String[] url;
    int[] image;
    Context context;
    public ShoppingAdapter(Context context, String[] data, String[] url, int[] image) {
        super(context, R.layout.shoppinglist, data);
        this.context = context;
        this.image = image;
        this.data = data;
        this.url = url;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View singleItem = convertView;
        ShoppingViewHolder holder = null;
        if(singleItem == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            singleItem = layoutInflater.inflate(R.layout.shoppinglist, parent, false);

            holder = new ShoppingViewHolder(singleItem);
            singleItem.setTag(holder);
        }
        else{
            holder = (ShoppingViewHolder) singleItem.getTag();
        }
        holder.logo.setImageResource(image[position]);
//        holder.name.setText(data[position]);

        singleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), data[position] + "(으)로 이동합니다.", Toast.LENGTH_SHORT).show();
                Intent openLinksIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url[position]));
                context.startActivity(openLinksIntent);
            }
        });
        return singleItem;
    }
}

