package com.iqra.carrenting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BookingActivity extends AppCompatActivity {


    private String mPost_key = null;
    private DatabaseReference mDatabase;

    private TextView carType;
    private ImageView carImage;
    private TextView carModel;
    private TextView carPrice;
    private TextView engineSize;
    private Button btnBook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        mDatabase = FirebaseDatabase.getInstance().getReference("Cars");
        mPost_key = getIntent().getExtras().getString("blog_id");

        carImage = findViewById(R.id.imageView2);
        carType = findViewById(R.id.carType);
        carModel = findViewById(R.id.carmodel);
        carPrice = findViewById(R.id.carprice);
        engineSize = findViewById(R.id.engineSize);
        btnBook = findViewById(R.id.btnBook);

        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {

                String post_image = (String) snapshot.child("image").getValue();
                String post_type = (String) snapshot.child("tweet").getValue();
                String post_model = (String) snapshot.child("car_model").getValue();
                String post_price = (String) snapshot.child("car_price").getValue();
                String engine_size = (String) snapshot.child("engine_size").getValue();


                carType.setText(post_type);
                carModel.setText(post_model);
                carPrice.setText("KES. " + post_price);
                engineSize.setText("Engine Size: " + engine_size);

                Picasso.get().load(post_image).into(carImage);

                btnBook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(BookingActivity.this, PaymentsActivity.class);
                        intent.putExtra("car_price", post_price);
                        startActivity(intent);
                    }
                });



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}