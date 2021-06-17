package com.iqra.carrenting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class NewPostActivity extends AppCompatActivity {

    private static  final int GALLERY_REQUEST = 1;
    private EditText captionText;
    private EditText captionModel;
    private EditText captionEngine;
    private EditText captionPrice;
    private FirebaseAuth mAuth;

    private Button btnTweet;
    private ImageButton selectImage;

    private Uri mImageUri = null;
    private ProgressDialog mDialog;
    private StorageReference mReference;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        captionText = findViewById(R.id.edittextCaption);
        btnTweet = findViewById(R.id.buttonTweet);
        selectImage = findViewById(R.id.imageView);

        captionPrice = findViewById(R.id.editTextPrice);
        captionModel = findViewById(R.id.editTextModel);
        captionEngine = findViewById(R.id.editTextEngine);

        mAuth = FirebaseAuth.getInstance();



        mDialog = new ProgressDialog(this);
        mReference = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference("Cars");

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(galleryIntent,GALLERY_REQUEST);

            }
        });


        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTweeting();
            }
        });


    }

    private void startTweeting(){

        mDialog.setMessage("Posting......");
        final String tweet_caption = captionText.getText().toString().trim();
        final String tweet_price = captionPrice.getText().toString().trim();
        final String tweet_model = captionModel.getText().toString().trim();
        final String tweet_engine = captionEngine.getText().toString().trim();


        if(!TextUtils.isEmpty(tweet_caption) && mImageUri !=null){

            mDialog.show();

            final StorageReference filepath = mReference.child("CarImages").child(mImageUri.getLastPathSegment());
            final DatabaseReference newTweet = mDatabase.push();

            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    String user_id = mAuth.getCurrentUser().getUid();

                    newTweet.child("tweet").setValue(tweet_caption);
                    newTweet.child("tweet_id").setValue(newTweet.getKey());

                    newTweet.child("engine_size").setValue(tweet_engine);
                    newTweet.child("user_id").setValue(user_id);
                    newTweet.child("car_model").setValue(tweet_model);
                    newTweet.child("car_price").setValue(tweet_price);


                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();

                            newTweet.child("image").setValue(url);
                            mDialog.dismiss();

                            Intent intent = new Intent(NewPostActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                    });


                }
            });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);
        mImageUri = data.getData();
        selectImage.setImageURI(mImageUri);
    }



}