package com.prepup.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prepup.R;


/**
 * Created by kamalverma on 25/12/16.
 */

public class HomePageFragment extends Fragment {


    public static String TAG = HomePageFragment.class.getName();


    public static HomePageFragment getInstance() {
        return new HomePageFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, null);
        return view;
    }

}
