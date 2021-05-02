package com.example.postover.ui.CALENDAR;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postover.Model.CalendarNote;
import com.example.postover.Model.ToDoNote;
import com.example.postover.R;
import com.example.postover.ui.TODO.TodoAdapter;

import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {
    private List<CalendarNote> calendarNotes;
    FragmentActivity mainActivity;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView day;
        private TextView numDay;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            day = view.findViewById(R.id.day_calendar_text);
            numDay = view.findViewById(R.id.num_calendar_text);
        }

        public TextView getTextView() {
            return day;
        }
    }

    public CalendarAdapter (FragmentActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    public void setTodoList(List<CalendarNote> dataSet) {
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
        holder.day.setText(calendarNotes.get(position).getDate().toString().substring(0,3));
        holder.numDay.setText(calendarNotes.get(position).getDate().toString().substring(8,10));

    }

    @Override
    public int getItemCount() {
        if(calendarNotes == null){return 0;}
        return  calendarNotes.size();
    }
}
