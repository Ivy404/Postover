package com.example.postover;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SplashFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SplashFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Handler handler = new Handler();
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                navController.navigate(R.id.action_splashFragment_to_viewPagerFragment);
            }
        };
        handler.postDelayed(runnable,3000);


        return inflater.inflate(R.layout.fragment_splash, container, false);
    }
}