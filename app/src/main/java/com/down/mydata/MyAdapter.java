package com.down.mydata;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Wrap me 09 on 11-08-2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myview> {
    Context context;
    ArrayList<bean> allData;

    public MyAdapter(Context context, ArrayList<bean> allData) {
        this.context = context;
        this.allData = allData;
    }

    @Override
    public MyAdapter.myview onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.recycle_items, null);
        MyAdapter.myview mh = new MyAdapter.myview(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.myview holder, int position) {
        holder.textView2.setText(allData.get(position).getContent_name());
        holder.textView4.setText(allData.get(position).getDownload_link());
        Glide.with(context)
                .load(allData.get(position).getImage_link())
                .placeholder(R.drawable.ic_menu_camera)
                .error(R.drawable.ic_menu_camera)
                .into(holder.imageView2);
    }

    @Override
    public int getItemCount() {
        System.out.println("size"+allData.size());
        return allData.size();
    }

    public class myview extends RecyclerView.ViewHolder {
        TextView textView2, textView4;
        ImageView imageView2;

        public myview(View itemView) {
            super(itemView);
            textView2=(TextView) itemView.findViewById(R.id.textView2);
            textView4=(TextView) itemView.findViewById(R.id.textView4);
            imageView2=(ImageView) itemView.findViewById(R.id.imageView2);

        }
    }
}
