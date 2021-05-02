package com.example.postover.ui.CALENDAR;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postover.Model.CalendarNote;
import com.example.postover.Model.Client;
import com.example.postover.Model.ToDoNote;
import com.example.postover.R;
import com.example.postover.ui.DialogCloseListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class CalendarFragment extends Fragment {

    RecyclerView recyclerView;
    CalendarAdapter calendarAdapter;
    InformationCalendarViewAdapter informationCalendarViewAdapter;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);

        CalendarView calendarView = root.findViewById(R.id.calendarView);

        recyclerView = root.findViewById(R.id.calendarRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        calendarAdapter = new CalendarAdapter(getActivity());
        recyclerView.setAdapter(calendarAdapter);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        root.findViewById(R.id.calendar_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCalendar addCalendar = new AddCalendar();
                addCalendar.show(getActivity().getSupportFragmentManager(),AddCalendar.TAG);
            }
        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                AddCalendar addCalendar = new AddCalendar(view,year,month,dayOfMonth);
                addCalendar.show(getActivity().getSupportFragmentManager(),AddCalendar.TAG);
            }
        });

        getList();
        return root;
    }

    public void getList() {
        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    Client client = task.getResult().getValue(Client.class);
                    HashMap<String,List<CalendarNote>> calendarNotes;
                    if(client.getHashCalendar() == null){
                        calendarNotes = new HashMap<>();
                    }
                    else {
                        calendarNotes = client.getHashCalendar();
                    }
                    //informationCalendarViewAdapter.setTodoList(calendarNotes);
                    calendarAdapter.setTodoList(calendarNotes);
                }
            }
        });
    }
}