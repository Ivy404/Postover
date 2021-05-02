package com.example.postover.ui.CALENDAR;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postover.Model.CalendarNote;
import com.example.postover.Model.ToDoNote;
import com.example.postover.R;
import com.example.postover.ui.TODO.TodoAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {
    private HashMap<String,List<CalendarNote>> calendarNotes;
    FragmentActivity mainActivity;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView day;
        private TextView numDay;
        private RecyclerView rvSubItem;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            day = view.findViewById(R.id.day_calendar_text);
            numDay = view.findViewById(R.id.num_calendar_text);
            rvSubItem = view.findViewById(R.id.recyclerviewinfo);
        }

        public TextView getTextView() {
            return day;
        }
    }

    public CalendarAdapter (FragmentActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    public void setTodoList(HashMap<String,List<CalendarNote>> dataSet) {
        calendarNotes = dataSet;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CalendarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_calendar_note, parent, false);
        return new CalendarAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarAdapter.ViewHolder holder, int position) {
        int i = 0;
        String key = "";
        List<CalendarNote> value = null;
        for(Map.Entry<String, List<CalendarNote>> entry : calendarNotes.entrySet()) {
             key = entry.getKey();
             value = entry.getValue();
            if (i == position){break;}
            i++;
        }
        holder.day.setText(calendarNotes.get(key).get(0).getDate().toString().substring(0,3));
        holder.numDay.setText(calendarNotes.get(key).get(0).getDate().toString().substring(8,10));

        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.rvSubItem.getContext(),
                LinearLayoutManager.VERTICAL,false);
        layoutManager.setInitialPrefetchItemCount(calendarNotes.size());

        InformationCalendarViewAdapter informationCalendarViewAdapter = new InformationCalendarViewAdapter();
        informationCalendarViewAdapter.setinformationlist(value);

        holder.rvSubItem.setLayoutManager(layoutManager);
        holder.rvSubItem.setAdapter(informationCalendarViewAdapter);
        holder.rvSubItem.setRecycledViewPool(viewPool);



    }

    @Override
    public int getItemCount() {
        if(calendarNotes == null){return 0;}
        return  calendarNotes.size();
    }
}


