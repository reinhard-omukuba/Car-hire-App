package com.iqra.carrenting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BookingActivity extends AppCompatActivity {


    private String mPost_key = null;
    private DatabaseReference mDatabase, mDatabase2;

    private TextView carType;
    private ImageView carImage;
    private TextView carModel;
    private TextView carPrice;
    private TextView engineSize;
    private Button btnBook;
    private Button btnReserve;

    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        mDatabase = FirebaseDatabase.getInstance().getReference("Cars");
        mDatabase2 = FirebaseDatabase.getInstance().getReference("Reservations");
        mPost_key = getIntent().getExtras().getString("blog_id");

        carImage = findViewById(R.id.imageView2);
        carType = findViewById(R.id.carType);
        carModel = findViewById(R.id.carmodel);
        carPrice = findViewById(R.id.carprice);
        engineSize = findViewById(R.id.engineSize);
        btnBook = findViewById(R.id.btnBook);
        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);

        btnReserve =findViewById(R.id.btnReserve);

        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {

                String post_image = (String) snapshot.child("image").getValue();
                String post_type = (String) snapshot.child("tweet").getValue();
                String post_model = (String) snapshot.child("car_model").getValue();
                String post_price = (String) snapshot.child("car_price").getValue();
                String engine_size = (String) snapshot.child("engine_size").getValue();

                String theuser_id = (String) snapshot.child("user_id").getValue();
                String user_id = mAuth.getCurrentUser().getUid();

                if (theuser_id.equals(user_id)){
                    btnBook.setVisibility(View.GONE);
                    btnReserve.setVisibility(View.GONE);
                }


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


       // mDatabase2.child("OZvwoh5aaYYrkJtP4BM0oIY5pAY2").orderByChild("carid").equalTo("-McMVoZFg2bV3gpzWBMV").addListenerForSingleValueEvent(new ValueEventListener() {

//        String datakey =  mDatabase2.getKey();
//        mDatabase2.child(datakey).addValueEventListener(new ValueEventListener()  {
//            @Override
//            public void onDataChange( DataSnapshot snapshot) {
//
//                String the_car_id = (String) snapshot.child("carid").getValue();
//                String the_user_id = (String) snapshot.child("myid").getValue();
//                Toast.makeText(BookingActivity.this, the_car_id, Toast.LENGTH_SHORT).show();
//                String myUserid = mAuth.getCurrentUser().getUid();
//                if (the_user_id.equals(myUserid)){
//                    btnReserve.setVisibility(View.GONE);
//                }
//
//            }
//
//            @Override
//            public void onCancelled( DatabaseError error) {
//
//            }
//        });





        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reserveCar();
            }
        });


    }

    public void reserveCar(){

        mDialog.setMessage("Please wait......");
        mDialog.show();

        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {

                String myUserid = mAuth.getCurrentUser().getUid();
                DatabaseReference current_user_db = mDatabase2.child(myUserid);
                current_user_db.child("myid").setValue(myUserid);
                current_user_db.child("carid").setValue(mPost_key);
                //current_user_db.child("carid").setValue(mPost_key);

                Toast.makeText(BookingActivity.this, "Vehicle Reserved", Toast.LENGTH_SHORT).show();
                mDialog.dismiss();

                Intent intent = new Intent(BookingActivity.this, HomeActivity.class);
                startActivity(intent);

            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });

    }



}