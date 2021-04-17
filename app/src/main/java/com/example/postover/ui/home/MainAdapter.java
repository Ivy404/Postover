package com.example.postover.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.postover.R;

public class MainAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    String [] number;
    int [] numberimage;


    public MainAdapter(Context context, LayoutInflater inflater, String[] number,int[] numberimage) {
        this.context = context;
        this.inflater = inflater;
        this.number = number;
        this.numberimage = numberimage;
    }

    @Override
    public int getCount() {
        return number.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null) inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView==null) convertView = inflater.inflate(R.layout.row_item,null);

        ImageView imageView = convertView.findViewById(R.id.imageView2);
        imageView.setImageResource(numberimage[position]);
        TextView textView= convertView.findViewById(R.id.textView2);
        textView.setText(number[position]);
        return convertView;
    }
}
