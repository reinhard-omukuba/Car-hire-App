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
import android.widget.EditText;
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
import com.google.firebase.database.ValueEventListener;
import com.iqra.carrenting.BookingActivity;
import com.squareup.picasso.Picasso;
import com.iqra.carrenting.MyAdapter;
import com.iqra.carrenting.NewPostActivity;
import com.iqra.carrenting.R;
import com.iqra.carrenting.model;


public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private  View view;
    private DatabaseReference postRef, likesRef;
    private FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    MyAdapter mAdapter;
    Boolean like_checker = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mAuth = FirebaseAuth.getInstance();
        postRef = FirebaseDatabase.getInstance().getReference().child("Cars");
        likesRef = FirebaseDatabase.getInstance().getReference().child("post_likes");


        view = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.posts_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(postRef, model.class)
                        .build();


        FirebaseRecyclerAdapter<model, PostsViewHolder> adapter
                = new FirebaseRecyclerAdapter<model, PostsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull HomeFragment.PostsViewHolder holder, int position, @NonNull model model) {


                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String currentUserId = currentUser.getUid();
                final String postKey = getRef(position).getKey();


                holder.bookride.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent singleBlogIntent = new Intent(getActivity(), BookingActivity.class);
                        singleBlogIntent.putExtra("blog_id", postKey);
                        startActivity(singleBlogIntent);
                    }
                });


                holder.caption.setText(model.getTweet() + " " + model.getCar_model());
                holder.captionPrice.setText("KES. " + model.getCar_price() + " per day");

                Picasso.get().load(model.getImage()).into(holder.mImageView);




                holder.setLikesButtonStatus(postKey);
                holder.mImageViewLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        like_checker = true;
                        likesRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                if(like_checker ==true){
                                    if(snapshot.child(postKey).hasChild(currentUserId)){
                                        likesRef.child(postKey).child(currentUserId).removeValue();
                                        like_checker = false;
                                    }else{

                                        likesRef.child(postKey).child(currentUserId).setValue(true);
                                        like_checker = false;


                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });


            }



            @NonNull
            @Override
            public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
                PostsViewHolder postsViewHolder = new PostsViewHolder(view);
                return postsViewHolder;
            }
        };

        mRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static class PostsViewHolder extends RecyclerView.ViewHolder{

        TextView caption;
        ImageView mImageView;
        ImageButton mImageViewLike;
        TextView totalLikes;
        int likesCount;
        DatabaseReference likesRef;


        TextView captionPrice;
        LinearLayout bookride;


        public void setLikesButtonStatus(final String postKey){

            mImageViewLike = itemView.findViewById(R.id.imageview_like);
            totalLikes = itemView.findViewById(R.id.tv_total_likes);
            likesRef = FirebaseDatabase.getInstance().getReference("post_likes");
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            String userId = currentUser.getUid();
            String likes = "post_likes";

            likesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.child(postKey).hasChild(userId)){
                        likesCount = (int)snapshot.child(postKey).getChildrenCount();
                        mImageViewLike.setImageResource(R.drawable.like);
                        totalLikes.setText(Integer.toString(likesCount) +" likes");
                    }else{

                        likesCount = (int)snapshot.child(postKey).getChildrenCount();
                        mImageViewLike.setImageResource(R.drawable.dislike);
                        totalLikes.setText(Integer.toString(likesCount) +" likes");

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }



        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);
            caption = itemView.findViewById(R.id.textView_caption);
            captionPrice = itemView.findViewById(R.id.textView5);
            mImageView = itemView.findViewById(R.id.imageView_posts);
            bookride = itemView.findViewById(R.id.bookride);
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
        // Here notify the fragment that it should participate in options menu handling.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.options_menu, menu);
        MenuItem item = menu.findItem(R.id.camera);
        item.setVisible(false);
        MenuItem item1 = menu.findItem(R.id.wishlist);
        item1.setVisible(false);
        MenuItem item2 = menu.findItem(R.id.more);
        item2.setVisible(false);
        return;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.new_post) {


            Intent intent = new Intent(getActivity(), NewPostActivity.class);
            startActivity(intent);
            //your function here
            Toast.makeText(getActivity(), "Create new Post", Toast.LENGTH_SHORT).show();
        }

//        if (id == R.id.likes) {
//            //your function here
//            Toast.makeText(getActivity(), "Likes", Toast.LENGTH_SHORT).show();
        //}
        if (id == R.id.messages) {
            //your function here
            Toast.makeText(getActivity(), "Messages", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}