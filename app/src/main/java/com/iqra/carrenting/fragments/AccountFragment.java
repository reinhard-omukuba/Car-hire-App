package com.iqra.carrenting.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iqra.carrenting.R;
import com.iqra.carrenting.UpdateAccount;

import org.w3c.dom.Text;


public class AccountFragment extends Fragment {



    public AccountFragment() {
        // Required empty public constructor
    }

    private TextView tvUserName;
    private TextView tvEmail;

    private TextView textViewTown;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private Button updateAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);




        tvUserName = view.findViewById(R.id.tvUserName);
        tvEmail = view.findViewById(R.id.tvEmail);
        textViewTown = view.findViewById(R.id.textViewTown);
        updateAccount = view.findViewById(R.id.updateAccount);


        mAuth =FirebaseAuth.getInstance();
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String userid=user.getUid();

        tvEmail.setText(user.getEmail());

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(userid);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {

                String username = (String) snapshot.child("full_name").getValue();
                String usertown = (String) snapshot.child("usertown").getValue();

                tvUserName.setText(username);
                textViewTown.setText(usertown);

            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });



        updateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UpdateAccount.class);
                startActivity(intent);
            }
        });





        return view;
    }


}