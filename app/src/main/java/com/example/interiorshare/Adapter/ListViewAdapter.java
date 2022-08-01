package com.example.interiorshare.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.interiorshare.R;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;

    public ListViewAdapter() {

    }

    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    @Override
    public View getView(int position, View View, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (View == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View = inflater.inflate(R.layout.list, parent, false);
        }
        TextView title = (TextView) View.findViewById(R.id.content_title) ;
        TextView content = (TextView) View.findViewById(R.id.content_content) ;
        TextView detail = (TextView) View.findViewById(R.id.content_detail) ;
        ListViewItem listViewItem = listViewItemList.get(position);

        title.setText(listViewItem.getTitle());
        content.setText(listViewItem.getContent());
        detail.setText(listViewItem.getDetail());
        return View;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    public void addItem(String title,String content, String detail) {
        ListViewItem item = new ListViewItem();
        item.setTitle(title);
        item.setContent(content);
        item.setDetail(detail);
        listViewItemList.add(item);
    }
}

