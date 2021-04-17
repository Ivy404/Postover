package com.example.postover.onboarding;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.postover.ui.gallery.GalleryFragment;
import com.example.postover.ui.home.HomeFragment;
import com.example.postover.ui.slideshow.SlideshowFragment;

import java.util.List;

public class AdapterSlide extends FragmentStateAdapter {


    private List<Fragment> fragmentList;

    public AdapterSlide(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Fragment> fragmentList) {
        super(fragmentManager, lifecycle);
        this.fragmentList=fragmentList;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
