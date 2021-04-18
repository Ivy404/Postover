package com.example.postover.onboarding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.postover.R;
import com.example.postover.ui.gallery.GalleryFragment;
import com.example.postover.ui.home.HomeFragment;
import com.example.postover.ui.slideshow.SlideshowFragment;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerFragment extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_view_pager, container, false);

        List <Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new GalleryFragment());
        fragmentList.add(new HomeFragment());
        fragmentList.add(new SlideshowFragment());

        AdapterSlide adapter = new AdapterSlide(requireActivity().getSupportFragmentManager(),getLifecycle(),fragmentList);

        ViewPager2 viewPager2 = root.findViewById(R.id.view_pager2);

        viewPager2.setAdapter(adapter);
        viewPager2.setCurrentItem(1);


        root.findViewById(R.id.todo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager2.setCurrentItem(0);
            }
        });
        root.findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager2.setCurrentItem(1);
            }
        });
        root.findViewById(R.id.calendar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager2.setCurrentItem(2);
            }
        });


        return root;


    }
}