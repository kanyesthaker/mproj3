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
    public static Context context;
    private ImageView img;
    StorageReference imgref;
    Utils util = new Utils();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_social);

        util.socialCreationPage();
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
                util.setImg(imgref.getDownloadUrl().toString(), img, img.getId());
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case (R.id.addImage):
                //Get an image from another app
                startActivityForResult((new Intent(Intent.ACTION_GET_CONTENT)).setType("image/*"), 1);
                break;
            case (R.id.submit):
                if (result != null) {
                    //Make a new social using method from Utils
                    imgref.putFile(result).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(NewSocialActivity.this, "dammit", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            util.newSocial();
                        }
                    });
                }
        }
    }
}
