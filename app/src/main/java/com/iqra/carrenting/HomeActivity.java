package com.iqra.carrenting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.iqra.carrenting.fragments.HomeFragment;
import com.iqra.carrenting.fragments.ProfileFragment;
import com.iqra.carrenting.fragments.ReelsFragment;
import com.iqra.carrenting.fragments.SearchFragment;
import com.iqra.carrenting.fragments.ShopFragment;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, new HomeFragment()).commit();
        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;

                switch (item.getItemId()){
                    case R.id.home:
                        fragment = new HomeFragment();
                        break;

                    case R.id.search:
                        fragment = new SearchFragment();
                        break;


//                    case R.id.reels:
//                        Intent intent = new Intent(HomeActivity.this, NewPostActivity.class);
//                        startActivity(intent);
//                        break;

                    case R.id.shop:
                        fragment = new ShopFragment();
                        break;

                    case R.id.profile:
                        fragment = new ProfileFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, fragment).commit();

                return true;
            }
        });

    }
}