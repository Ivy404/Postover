package com.example.postover.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.postover.Model.HomeNote;
import com.example.postover.Model.ToDoNote;
import com.example.postover.R;

import java.util.List;

public class MainAdapter extends BaseAdapter {

    private final Context mContext;
    private List<HomeNote> notes;

    // 1
    public MainAdapter(Context context, List<HomeNote> notes) {
        this.mContext = context;
        this.notes = notes;
    }

    // 2
    @Override
    public int getCount() {
        return notes.size();
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView dummyTextView = new TextView(mContext);
        dummyTextView.setText(this.notes.get(position).getTitle());
        return dummyTextView;
    }

    public void setHomeNotes(List<HomeNote> dataSet) {
        notes = dataSet;
        notifyDataSetChanged();
    }

}