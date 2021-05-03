package com.example.postover.ui.CALENDAR;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postover.Model.CalendarNote;
import com.example.postover.R;


import java.util.List;

public class InformationCalendarViewAdapter extends RecyclerView.Adapter<InformationCalendarViewAdapter.ViewHolder> {
    private List<CalendarNote> calendarlistinfo;
    FragmentActivity mainActivity;

    public static class ViewHolder extends RecyclerView.ViewHolder {
    private TextView calendarInfo,calendarSubInfo;

    public ViewHolder(View view) {
        super(view);
        calendarInfo = view.findViewById(R.id.calendar_info_text);
        calendarSubInfo = view.findViewById(R.id.calendar_subInfo);

    }
    public TextView getTextView() {
        return calendarInfo;
    }
}
    public InformationCalendarViewAdapter() {

    }
    public void setinformationlist(List<CalendarNote> dataSet) {
        calendarlistinfo = dataSet;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InformationCalendarViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_information_calendar, parent, false);
        return new InformationCalendarViewAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull InformationCalendarViewAdapter.ViewHolder holder, int position) {

        holder.calendarInfo.setText(calendarlistinfo.get(position).getTitle());
        holder.calendarSubInfo.setText(calendarlistinfo.get(position).getSubtitle());
    }

    @Override
    public int getItemCount() {
        if(calendarlistinfo == null){return 0;}
        return  calendarlistinfo.size();
    }

}
