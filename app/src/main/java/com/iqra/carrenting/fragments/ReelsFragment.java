package com.iqra.carrenting.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.iqra.carrenting.R;



public class ReelsFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Here notify the fragment that it should participate in options menu handling.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reels, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull  Menu menu,  MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.options_menu, menu);

        MenuItem item = menu.findItem(R.id.more);
        item.setVisible(false);
        MenuItem item1 = menu.findItem(R.id.messages);
        item1.setVisible(false);
        MenuItem item2 = menu.findItem(R.id.new_post);
        item2.setVisible(false);
        MenuItem item3 = menu.findItem(R.id.wishlist);
        item3.setVisible(false);
//        MenuItem item4 = menu.findItem(R.id.likes);
//        item4.setVisible(false);


        return;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}