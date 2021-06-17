package com.iqra.carrenting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText mEditTextName;
    private EditText mEditTextEmail;
    private EditText mEditTextPass;
    private Button mButtonRegister;
    private ProgressDialog mDialog;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        mEditTextName = findViewById(R.id.editTextName);
        mEditTextEmail = findViewById(R.id.et_Email);
        mEditTextPass = findViewById(R.id.editTextPass);
        mButtonRegister = findViewById(R.id.btn_register);

        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        mDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();


        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser() {

        String fullname = mEditTextName.getText().toString().trim();
        String email = mEditTextEmail.getText().toString().trim();
        String password = mEditTextPass.getText().toString().trim();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {


            mDialog.setMessage("Subiri kidogo......");
            mDialog.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {


                    if (task.isSuccessful()) {


                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = mDatabase.child(user_id);
                        current_user_db.child("id").setValue(user_id);
                        current_user_db.child("email").setValue(email);
                        current_user_db.child("full_name").setValue(fullname);
                        current_user_db.child("usertown").setValue("usertown");


                        Toast.makeText(RegisterActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();

                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                        startActivity(intent);

                    } else {

                        Toast.makeText(RegisterActivity.this, "Registration FAiled", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();

                    }


                }
            });

        }else{

            Toast.makeText(RegisterActivity.this, "Please Fill all the fields", Toast.LENGTH_SHORT).show();

        }
    }

    public void toLogin(View view) {

        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}