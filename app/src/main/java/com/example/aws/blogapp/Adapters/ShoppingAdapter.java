package com.example.aws.blogapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aws.blogapp.Models.Shopping;
import com.example.aws.blogapp.R;

import java.util.ArrayList;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder> {

    private ArrayList<Shopping> dataList;
    private Context context;
    private Intent intent;
    ImageView imageView;

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    private OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public interface OnLongItemClickListener {
        void onLongItemClick(int pos);
    }

    private OnLongItemClickListener onLongItemClickListener = null;

    public void setOnLongItemClickListener(OnLongItemClickListener listener) {
        this.onLongItemClickListener = listener;
    }

    public ShoppingAdapter(Context context, ArrayList<Shopping> dataList) {
        this.context = context;
        this.dataList = dataList;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_shopping, parent, false);

        return new ShoppingAdapter.ViewHolder(itemView);



    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(@NonNull ShoppingAdapter.ViewHolder holder, int position) {
        Shopping item = dataList.get(position);
        holder.productId.setText(item.getProductId());
        holder.price.setText(item.getPrice());
        Glide.with(context).load(item.getProductImg()).into(holder.productImg);


    }




    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
            return dataList.size() ;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView productId ;
        TextView price ;
        ImageView productImg;

        ViewHolder(@NonNull View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)

            productId = itemView.findViewById(R.id.productId) ;
            price = itemView.findViewById(R.id.price) ;
            productImg = itemView.findViewById(R.id.productImg);

            productImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(dataList.get(getAdapterPosition()).getlink()));
                    context.startActivity(intent);
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });

            productImg.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (onLongItemClickListener != null) {
                            onLongItemClickListener.onLongItemClick(position);
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
    }
}

