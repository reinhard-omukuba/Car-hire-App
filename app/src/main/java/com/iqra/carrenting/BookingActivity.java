package com.iqra.carrenting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookingActivity extends AppCompatActivity {


    private String mPost_key = null;
    private DatabaseReference mDatabase;

    private TextView carType;
    private ImageView carImage;
    private TextView carModel;
    private TextView carPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        mDatabase = FirebaseDatabase.getInstance().getReference("Cars");
        mPost_key = getIntent().getExtras().getString("tweet_id");
        carImage = findViewById(R.id.imageView2);
        carType = findViewById(R.id.carType);
        carModel = findViewById(R.id.carmodel);
        carPrice = findViewById(R.id.carprice);

//        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull  DataSnapshot snapshot) {
//
//                String post_image = (String) snapshot.child("image").getValue();
//                String post_type = (String) snapshot.child("tweet").getValue();
//                String post_model = (String) snapshot.child("car_model").getValue();
//                String post_price = (String) snapshot.child("car_price").getValue();
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });




    }
}