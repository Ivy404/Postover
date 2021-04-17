package com.example.postover.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.postover.MainActivity;
import com.example.postover.R;

public class HomeFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        String [] number = {"1", "2","3"};
        int [] numberimage= {R.drawable.one,R.drawable.two,R.drawable.three};

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        GridView gridView = root.findViewById(R.id.grid_view) ;
        MainAdapter mainAdapter = new MainAdapter(getActivity(),inflater,number,numberimage);
        gridView.setAdapter(mainAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"You clicked "+number[+position],Toast.LENGTH_SHORT).show();
            }
        });


        return root;
    }
}