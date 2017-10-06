package com.example.kanyes.mdbsocials;

import android.content.Context;
import android.content.Intent;
import android.media.audiofx.AudioEffect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by Kanyes on 9/28/2017.
 */

public class NewSocialActivity extends AppCompatActivity implements View.OnClickListener{
    private Uri result;
    private StorageReference mStorageRef;
    Button submit, addImage;
    Context context;
    private ImageView img;
    StorageReference sref, imgref;
    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    final String key = ref.child("socials").push().getKey();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_social);

        sref = FirebaseStorage.getInstance().getReferenceFromUrl("gs://mdbsocials-d0158.appspot.com");
        imgref = sref.child(key + ".png");
        submit = (Button) findViewById(R.id.submit);
        addImage = (Button) findViewById(R.id.addImage);
        final EditText newSocialName = (EditText) findViewById(R.id.newSocialName);
        final EditText newSocialDate = (EditText) findViewById(R.id.newSocialDate);
        final EditText newSocialDescription = (EditText) findViewById(R.id.newSocialDescription);
        img = (ImageView) findViewById(R.id.newSocialImage);



        mStorageRef = FirebaseStorage.getInstance().getReference();
        addImage.setOnClickListener(this);
        submit.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            result=data.getData();
            if (imgref != null){
                Glide.with(NewSocialActivity.this).using(new FirebaseImageLoader()).load(imgref).into(img);
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case (R.id.addImage):
                startActivityForResult((new Intent(Intent.ACTION_GET_CONTENT)).setType("image/*"), 1);
                break;
            case (R.id.submit):
                if (result != null) {
                    imgref.putFile(result).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(NewSocialActivity.this, "dammit", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String event = ((EditText) findViewById(R.id.newSocialName)).getText().toString();
                            String date = ((EditText) findViewById(R.id.newSocialDate)).getText().toString();
                            String email = LoginActivity.email;
                            String interested = "0";
                            String description = ((EditText) findViewById(R.id.newSocialDescription)).getText().toString();
                            ref.child("socials").child(key).child("event").setValue(event);
                            ref.child("socials").child(key).child("date").setValue(date);
                            ref.child("socials").child(key).child("email").setValue(email);
                            ref.child("socials").child(key).child("interested").setValue(interested);
                            ref.child("socials").child(key).child("description").setValue(description);
                            startActivity(new Intent(NewSocialActivity.this, FeedActivity.class));
                        }
                    });
                }
        }
    }
}
