package com.iqra.carrenting.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.iqra.carrenting.R;

public class SearchFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.options_menu, menu);

        MenuItem item = menu.findItem(R.id.camera);
        item.setVisible(false);
        MenuItem item1 = menu.findItem(R.id.wishlist);
        item1.setVisible(false);
        MenuItem item2 = menu.findItem(R.id.more);
        item2.setVisible(false);
        MenuItem item3 = menu.findItem(R.id.new_post);
        item3.setVisible(false);
//        MenuItem item4 = menu.findItem(R.id.likes);
//        item4.setVisible(false);
        MenuItem item5 = menu.findItem(R.id.messages);
        item5.setVisible(false);
        return;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}