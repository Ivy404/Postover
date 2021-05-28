package com.example.postover.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postover.CreateNoteActivity;
import com.example.postover.MainActivity;
import com.example.postover.Model.Client;
import com.example.postover.Model.HomeNote;
import com.example.postover.R;
import com.example.postover.ui.ActivityRegister;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;
    private List<HomeNote> homeNotes;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeNotes = new ArrayList<>();
        root.findViewById(R.id.home_fab).setOnClickListener(this::AddNote);
        mainAdapter = new MainAdapter(homeNotes, getActivity());


        recyclerView = root.findViewById(R.id.homeRecyclerView);
        int spacingInPixels = 16;
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mainAdapter);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(),CreateNoteActivity.class);
                        intent.putExtra("note",homeNotes.get(position));
                        intent.putExtra("position",position);
                        startActivityForResult(intent,1);

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Delete Task");
                        builder.setMessage("Are you sure you want to delete this Note?");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteNote(position);
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

    private void deleteNote(int position) {
        DatabaseReference reference = mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child("homeNoteList");

        Task<DataSnapshot> query = reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                List<HomeNote> homenotes = (List<HomeNote>) task.getResult().getValue();
                homenotes.remove(position);
                homenotes.removeAll(Collections.singletonList(null));
                mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child("homeNoteList").setValue(homenotes);
                getList();
            }
        });
            }


    public void AddNote(View view){
        //AddHomeNote.newInstance().show(getActivity().getSupportFragmentManager(),AddHomeNote.TAG);
        Intent intent = new Intent(getActivity(), CreateNoteActivity.class);
        startActivityForResult(intent,1);
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
                    if(client.getHomeNoteList() == null){
                        homeNotes = new ArrayList<>();
                    }
                    else {
                        homeNotes = client.getHomeNoteList();
                    }
                    mainAdapter.setNotes(homeNotes);
                }
            }
        });
    }
}