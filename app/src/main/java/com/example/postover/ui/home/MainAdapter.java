package com.example.postover.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postover.Model.HomeNote;
import com.example.postover.Model.ToDoNote;
import com.example.postover.R;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private List<HomeNote> notes;
    private LayoutInflater mInflater;
    private Context context;

    public MainAdapter(List<HomeNote> homeNotes, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.notes = homeNotes;
        this.context = context;
    }

    public int getItemCount(){
        return notes.size();
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_homenote_task, null);
        return new MainAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        holder.bindData(notes.get(position));
    }

    public void setNotes(List<HomeNote> notes){
        this.notes = notes;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView text;
        ViewHolder(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.cardTitle);
            text = itemView.findViewById(R.id.cardText);
        }
        void bindData(@NotNull HomeNote note){
            title.setText(note.getTitle());
            text.setText(note.getText());
        }


    }
}