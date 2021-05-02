package com.example.postover.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postover.MainActivity;
import com.example.postover.Model.HomeNote;
import com.example.postover.Model.Note;
import com.example.postover.Model.ToDoNote;
import com.example.postover.R;
import com.example.postover.ui.ActivityRegister;
import com.example.postover.ui.TODO.AddTodo;
import com.example.postover.ui.TODO.TodoAdapter;
import com.getbase.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {
    private GridView gridView;
    private MainAdapter mainAdapter;
    private  List<HomeNote> homeNotes;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeNotes = new ArrayList<>();
        root.findViewById(R.id.home_fab).setOnClickListener(this::AddNote);
        gridView = root.findViewById(R.id.grid_view);
        mainAdapter = new MainAdapter(getActivity(), homeNotes);
        gridView.setAdapter(mainAdapter);
        /*gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "You clicked " + number[+position], Toast.LENGTH_SHORT).show();
            }
        });*/

        return root;
    }

    public void AddNote(View view){
        AddHomeNote.newInstance().show(getActivity().getSupportFragmentManager(),AddTodo.TAG);
    }
}