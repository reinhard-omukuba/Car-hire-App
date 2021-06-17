package com.iqra.carrenting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class SearchActivity extends AppCompatActivity {

    private EditText mSearchField;
    private ImageButton mSearchBtn;

    private RecyclerView mResultList;

    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mUserDatabase = FirebaseDatabase.getInstance().getReference("Cars");

//
//        mSearchField = (EditText) findViewById(R.id.search_field);
//        mSearchBtn = (ImageButton) findViewById(R.id.search_btn);
//
//        mResultList = (RecyclerView) findViewById(R.id.result_list);
//        mResultList.setHasFixedSize(true);
//        mResultList.setLayoutManager(new LinearLayoutManager(this));
//
//        mSearchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String searchText = mSearchField.getText().toString();
//
//                firebaseUserSearch(searchText);
//
//
//            }
//        });
//    }
//
//    private void firebaseUserSearch(String searchText) {
//
//        Toast.makeText(SearchActivity.this, "Searching.....", Toast.LENGTH_LONG).show();
//
//        Query firebaseSearchQuery = mUserDatabase.orderByChild("title").startAt(searchText).endAt(searchText + "\uf8ff");
//
//
//
//        FirebaseRecyclerAdapter<model, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<model, UsersViewHolder>(
//
//                model.class,
//                R.layout.list_layout,
//                UsersViewHolder.class,
//                firebaseSearchQuery
//
//
//        ) {
//            //@NonNull
////            @Override
////            public UsersViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
////                return null;
////            }
////
////            @Override
////            protected void onBindViewHolder(SearchActivity.UsersViewHolder holder, int position, @NonNull @org.jetbrains.annotations.NotNull model model) {
////
////            }
//
//            @Override
//            protected void populateViewHolder(UsersViewHolder viewHolder, model model, int position) {
//
//
//                viewHolder.setDetails(getApplicationContext(), model.getTweet(), model.getCar_model(), model.getImage());
//
//            }
//        };
//
//        mResultList.setAdapter(firebaseRecyclerAdapter);
//
//    }
//
//
//    // View Holder Class
//
//    public static class UsersViewHolder extends RecyclerView.ViewHolder {
//
//        View mView;
//
//        public UsersViewHolder(View itemView) {
//            super(itemView);
//
//            mView = itemView;
//
//        }
//
//        public void setDetails(Context ctx, String userTitle, String userPhone, String userImage){
//
////            TextView user_title = (TextView) mView.findViewById(R.id.name_text);
////            TextView user_phone = (TextView) mView.findViewById(R.id.status_text);
////            ImageView user_image = (ImageView) mView.findViewById(R.id.profile_image);
//
//
////            user_title.setText(userTitle);
////            user_phone.setText(userPhone);
//
//
//            //Glide.with(ctx).load(userImage).into(user_image);


       // }

    }
}