package com.example.postover.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.postover.Model.HomeNote;
import com.example.postover.R;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ArrayAdapter<HomeNote> {

    private int resourceLayout;
    private Context mContext;

    public NoteAdapter(Context context, int resource, List<HomeNote> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        HomeNote p = getItem(position);

        if (p != null) {
            EditText tt1 = (EditText) v.findViewById(R.id.textNote);

            if (tt1 != null) {
                tt1.setText(p.getText());
            }

        }

        return v;
    }

}
