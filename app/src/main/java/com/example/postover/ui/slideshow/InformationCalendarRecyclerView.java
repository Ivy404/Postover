package com.example.postover.ui.slideshow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postover.Model.ToDoNote;
import com.example.postover.R;
import com.example.postover.ui.TODO.TodoAdapter;

import java.util.List;

public class InformationCalendarRecyclerView extends RecyclerView.Adapter<InformationCalendarRecyclerView.ViewHolder> {
    private List<calendarInformation> calendarlistinfo;
    FragmentActivity mainActivity;

    public static class ViewHolder extends RecyclerView.ViewHolder {
    private TextView calendarInfo,calendarSubInfo;

    public ViewHolder(View view) {
        super(view);
        calendarInfo = view.findViewById(R.id.calendar_info_text);
        calendarSubInfo = view.findViewById(R.id.calendar_info_text);

    }

    public TextView getTextView() {
        return calendarInfo;
    }
}

    public InformationCalendarRecyclerView(FragmentActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    public void setTodoList(List<calendarInformation> dataSet) {
        calendarlistinfo = dataSet;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InformationCalendarRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_information_calendar, parent, false);
        return new InformationCalendarRecyclerView.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull InformationCalendarRecyclerView.ViewHolder holder, int position) {
        holder.calendarInfo.setText(calendarlistinfo.get(position).getMainInfo());
        holder.calendarSubInfo.setText(calendarlistinfo.get(position).getSubInfo());
    }

    @Override
    public int getItemCount() {
        if(calendarlistinfo == null){return 0;}
        return  calendarlistinfo.size();
    }

}
