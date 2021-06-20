package com.iqra.carrenting.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.iqra.carrenting.BookingActivity;
import com.iqra.carrenting.Bookings;
import com.iqra.carrenting.HomeActivity;
import com.iqra.carrenting.MyAdapter;
import com.iqra.carrenting.R;
import com.iqra.carrenting.model;
import com.squareup.picasso.Picasso;


public class ShopFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private  View view;
    private DatabaseReference postRef;
    private FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_shop, container, false);

        mAuth = FirebaseAuth.getInstance();
        postRef = FirebaseDatabase.getInstance().getReference().child("Bookings");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.posts_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();


        //pull stuff

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = currentUser.getUid();

        Query firebaseSearchQuery = postRef.orderByChild("userid").startAt(currentUserId).endAt(currentUserId + "\uf8ff");

        FirebaseRecyclerOptions<Bookings> options = new FirebaseRecyclerOptions.Builder<Bookings>().setQuery(firebaseSearchQuery, Bookings.class).build();

        FirebaseRecyclerAdapter<Bookings, ShopFragment.PostsViewHolder> adapter = new FirebaseRecyclerAdapter<Bookings, ShopFragment.PostsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ShopFragment.PostsViewHolder holder, int position, @NonNull Bookings model) {


               final String postKey = getRef(position).getKey();

                holder.btnCncel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        postRef.child(postKey).removeValue();
                        Toast.makeText(getContext(), "Booking cancelled", Toast.LENGTH_SHORT).show();
                        Intent singleBlogIntent = new Intent(getActivity(), HomeActivity.class);
                        startActivity(singleBlogIntent);
                    }
                });


                holder.carName.setText(model.getCar_name());
                holder.tv_carengine.setText(model.getEngine_size());
                holder.tv_carprice.setText(model.getBooking_price());
                holder.tv_cardays.setText(model.getBooking_durations());


            }



            @NonNull
            @Override
            public ShopFragment.PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_booking, parent, false);
                ShopFragment.PostsViewHolder postsViewHolder = new ShopFragment.PostsViewHolder(view);
                //ShopFragment.PostsViewHolder postsViewHolder = new ShopFragment().PostsViewHolder(view);
                return postsViewHolder;
            }
        };

        mRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static class PostsViewHolder extends RecyclerView.ViewHolder{

        TextView carName;
        TextView tv_carengine;
        TextView tv_carprice;
        TextView tv_cardays;
        TextView btnCncel;

        LinearLayout bookride;



        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);
            carName = itemView.findViewById(R.id.tv_carname);
            tv_carengine = itemView.findViewById(R.id.tv_carengine);
            tv_carprice = itemView.findViewById(R.id.tv_carprice);
            tv_cardays = itemView.findViewById(R.id.tv_cardays);
            bookride = itemView.findViewById(R.id.bookrides);

            btnCncel = itemView.findViewById(R.id.btnCncel);
        }


    }

    @Override
    public void onStop() {
        super.onStop();

//        mAdapter.stopListening();
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.options_menu, menu);
        MenuItem item = menu.findItem(R.id.camera);
        item.setVisible(false);
        MenuItem item1 = menu.findItem(R.id.messages);
        item1.setVisible(false);
//        MenuItem item2 = menu.findItem(R.id.likes);
//        item2.setVisible(false);
        return;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}