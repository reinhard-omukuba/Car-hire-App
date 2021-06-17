package com.iqra.carrenting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateAccount extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText edtUsername;

    private EditText edtTown;
    private EditText edtAboutMe;
    private Button updtAcc;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String userid=user.getUid();

        edtUsername =findViewById(R.id.edtUsername);
        edtTown =findViewById(R.id.edtTown);
        edtAboutMe = findViewById(R.id.edtAboutMe);

        updtAcc = findViewById(R.id.updtAcc);

        mDialog = new ProgressDialog(this);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(userid);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {

                String username = (String) snapshot.child("full_name").getValue();
                String usertown = (String) snapshot.child("usertown").getValue();
                String userabout = (String) snapshot.child("userabout").getValue();

                edtUsername.setText(username);
                edtTown.setText(usertown);
                edtAboutMe.setText(userabout);
            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });

        updtAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAccount();
            }
        });



    }

    public void updateAccount(){


        String fullname = edtUsername.getText().toString().trim();
        String userabout = edtAboutMe.getText().toString().trim();
        String usertowwn = edtTown.getText().toString().trim();

        mDialog.setMessage("Subiri kidogo......");
        mDialog.show();


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {


                DatabaseReference current_user_db = mDatabase;
                current_user_db.child("full_name").setValue(fullname);
                current_user_db.child("usertown").setValue(usertowwn);
                current_user_db.child("userabout").setValue(userabout);



                Toast.makeText(UpdateAccount.this, "Account updated", Toast.LENGTH_SHORT).show();
                mDialog.dismiss();

                Intent intent = new Intent(UpdateAccount.this, HomeActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });


    }
}