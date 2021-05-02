package com.example.postover.ui.CALENDAR;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;


import com.example.postover.Model.CalendarNote;
import com.example.postover.Model.Client;
import com.example.postover.R;
import com.example.postover.ui.DialogCloseListener;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class AddCalendar extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";
    private EditText newCalendarNote;
    private EditText newCalendarNotesub;
    private Button newCalendarButton;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private CalendarView viewCalendar;
    private int year=-1,month,dayOfMonth;

    public static AddCalendar newInstance(){
        return new AddCalendar();
    }
    public  AddCalendar(CalendarView view, int year, int month, int dayOfMonth){
        this.viewCalendar=view;
        this.year=year;
        this.month=month;
        this.dayOfMonth=dayOfMonth;
    }
    public AddCalendar(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_calendar_new,container,false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        newCalendarNote = getView().findViewById(R.id.new_calendar_text);
        newCalendarButton = getView().findViewById(R.id.new_calendar_button);
        newCalendarNotesub = getView().findViewById(R.id.new_calendar_text_sub);
        newCalendarNotesub.setInputType(0);
        newCalendarButton.setEnabled(false);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        newCalendarNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    newCalendarButton.setEnabled(false);
                    newCalendarNotesub.setInputType(0);
                    newCalendarButton.setTextColor(Color.GRAY);
                }
                else{
                    newCalendarNotesub.setInputType(1);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        newCalendarNotesub.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    newCalendarButton.setEnabled(false);
                    newCalendarButton.setTextColor(Color.GRAY);
                }
                else{
                    newCalendarButton.setTextColor(ContextCompat.getColor(getContext(),R.color.lightred));
                    newCalendarButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        newCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(year != -1) createCalendarNote(viewCalendar,year,month,dayOfMonth);
                else{createCalendarNote(v);}
            }
        });

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Activity activity = getActivity();
        if (activity instanceof DialogCloseListener) {
            ((DialogCloseListener) activity).handleDialogClose(dialog,"CalendarNote");
        }
    }
    public void createCalendarNote(View view) {

        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.YEAR, year);

                        CalendarNote nota = new CalendarNote(newCalendarNote.getText().toString(), newCalendarNotesub.getText().toString(), calendar.getTime());
                        addCalendarNote(nota);

                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
                timePickerDialog.show();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    public void createCalendarNote(CalendarView view, int year, int month, int dayOfMonth) {

        final Calendar calendar = Calendar.getInstance();
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        TimePickerDialog tmd = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.YEAR, year);
                CalendarNote nota = new CalendarNote(newCalendarNote.getText().toString(), newCalendarNotesub.getText().toString(), calendar.getTime());
                addCalendarNote(nota);
            }
        },hora,min,true);
        tmd.show();

    }
    public void addCalendarNote(CalendarNote note){
        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    Client client = task.getResult().getValue(Client.class);
                    HashMap<String, List<CalendarNote>> calendarNoteList;
                    if(client.getHashCalendar() == null){
                        calendarNoteList = new HashMap<>();
                    }
                    else {
                        calendarNoteList = client.getHashCalendar();
                    }
                    if(calendarNoteList.containsKey(note.getDate().toString().substring(8,10))){
                        List<CalendarNote> calendarNotes = calendarNoteList.get(note.getDate().toString().substring(8,10));
                        calendarNotes.add(note); // añadido a la lista

                    }
                    else{
                        List<CalendarNote> calendarNotes = new ArrayList<>();
                        calendarNotes.add(note); // añadido a la lista
                        calendarNoteList.put(note.getDate().toString().substring(8,10),calendarNotes);

                    }
                    mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).setValue(client);
                    dismiss();
                }
            }

        });



    }

}


