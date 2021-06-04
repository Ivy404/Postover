package com.example.postover.ui.CALENDAR;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postover.CreateNoteActivity;
import com.example.postover.Model.CalendarNote;
import com.example.postover.Model.Client;
import com.example.postover.Model.HomeNote;
import com.example.postover.Model.Text;
import com.example.postover.Model.ToDoNote;
import com.example.postover.R;
import com.example.postover.ui.DialogCloseListener;
import com.example.postover.ui.home.RecyclerItemClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Delete Calendar Note");
                        builder.setMessage("Are you sure you want to delete this Calendar Note?");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteNote(position,view);
                                    }
                                });
                        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                })
        );
        getList();
        return root;
    }

    private void deleteNote(int position,View v) {


        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                Client client = task.getResult().getValue(Client.class);
                HashMap<String,List<CalendarNote>> calendarNotes;
                if(client.getHashCalendar() == null){
                    calendarNotes = new HashMap<>();
                }
                else {
                    calendarNotes = client.getHashCalendar();
                }

                CardView cardView = (CardView) recyclerView.getChildAt(position);
                RelativeLayout relativeLayout = (RelativeLayout) cardView.getChildAt(0);
                RecyclerView recyclerView = (RecyclerView) relativeLayout.getChildAt(3);
                LinearLayout linearLayout = (LinearLayout) recyclerView.getChildAt(0);
                ConstraintLayout constraintLayout = (ConstraintLayout) linearLayout.getChildAt(0);
                TextView textView = (TextView) constraintLayout.getChildAt(2);

                Set<Map.Entry<String,List<CalendarNote>>> setOfEntries  =  calendarNotes.entrySet();
                Iterator<Map.Entry<String,List<CalendarNote>>> iterator = setOfEntries .iterator();

                while(iterator.hasNext()){

                    Map.Entry<String,List<CalendarNote>> entry = iterator.next();
                    String s =calendarNotes.get(entry.getKey()).get(0).getDate().toString();

                    if (textView.getText().toString().equals(s) ){
                        calendarNotes.remove(entry.getKey());
                        break;}

                }
                mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child("hashCalendar").setValue(calendarNotes);
                getList();


            }
        });
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

                    String month_ = Calendar.getInstance().getTime().toString().substring(4,7);
                    ArrayList<Long >time = new ArrayList<>();

                    Set<Map.Entry<String,List<CalendarNote>>> setOfEntries  =  calendarNotes.entrySet();
                    Iterator<Map.Entry<String,List<CalendarNote>>> iterator = setOfEntries .iterator();

                    while(iterator.hasNext()){
                        Map.Entry<String,List<CalendarNote>> entry = iterator.next();
                        String s =calendarNotes.get(entry.getKey()).get(0).getDate().toString().substring(4,7);
                        if (month_.equals(s) ){
                            time.add(Long.parseLong(entry.getKey()));
                        }else{

                            iterator.remove();

                        }
                    }
                    Collections.sort(time);
                    mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child("hashCalendar").setValue(calendarNotes);
                    calendarAdapter.setTodoList(calendarNotes,time);
                }
            }
        });
    }
}