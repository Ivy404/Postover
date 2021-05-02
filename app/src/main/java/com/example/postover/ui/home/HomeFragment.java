package com.example.postover.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postover.MainActivity;
import com.example.postover.Model.Client;
import com.example.postover.Model.HomeNote;
import com.example.postover.Model.Note;
import com.example.postover.Model.ToDoNote;
import com.example.postover.R;
import com.example.postover.ui.ActivityRegister;
import com.example.postover.ui.TODO.AddTodo;
import com.example.postover.ui.TODO.TodoAdapter;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {
    private RecyclerView reciclerView;
    private MainAdapter mainAdapter;
    private List<HomeNote> homeNotes;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeNotes = new ArrayList<>();
        homeNotes.add(new HomeNote("Your First Note"));
        root.findViewById(R.id.home_fab).setOnClickListener(this::AddNote);
        mainAdapter = new MainAdapter(homeNotes, getActivity());


        reciclerView = root.findViewById(R.id.homeRecyclerView);
        int spacingInPixels = 16;
        reciclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        reciclerView.setLayoutManager(manager);
        reciclerView.setAdapter(mainAdapter);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        getList();

        /*gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "You clicked " + number[+position], Toast.LENGTH_SHORT).show();
            }
        });*/

        return root;
    }

    public void AddNote(View view){
        AddHomeNote.newInstance().show(getActivity().getSupportFragmentManager(),AddHomeNote.TAG);
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
                    List<HomeNote> homeNotes;
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