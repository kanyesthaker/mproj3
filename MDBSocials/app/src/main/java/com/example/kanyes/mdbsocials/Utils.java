package com.example.kanyes.mdbsocials;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Kanyes on 10/6/2017.
 */

public class Utils extends AppCompatActivity{
    private static Object Uri;
    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    final String key = ref.child("socials").push().getKey();
    final DatabaseReference specificRef = FirebaseDatabase.getInstance().getReference("/socials/"+SocialPage.i.getStringExtra("id"));
    public Utils(){

    }
    //animate ALL dem buttons fam
    public void animateButtons() {
        // int[] imageButtonIds = {R.id.animateButton};
        int[] viewIds = {R.id.imageView, R.id.emailView, R.id.passwordView, R.id.loginButton,R.id.signupButton};

        int i = 1;

        for (int viewId : viewIds) {
            // Button imageButton = (Button) findViewById(viewId);
            Animation fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fading_effect);
            fadeAnimation.setStartOffset(i * 250);
            //imageButton.startAnimation(fadeAnimation);

            int textViewId = viewIds[i-1];
            if (textViewId!=R.id.imageView) {
                TextView textView = (TextView) findViewById(textViewId);
                textView.startAnimation(fadeAnimation);
            }
            else{
                ImageView textView = (ImageView) findViewById(textViewId);
                textView.startAnimation(fadeAnimation);
            }
            i ++;
        }
        Log.d("Buttons animated fam", "animateButtons");
    }

    public void newSocial(){
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        final String key = ref.child("socials").push().getKey();
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
        startActivity(new Intent(NewSocialActivity.context, FeedActivity.class));
    }
    public void socialCreationPage(){
        StorageReference sref, imgref;
        sref = FirebaseStorage.getInstance().getReferenceFromUrl("gs://mdbsocials-d0158.appspot.com");
        imgref = sref.child(key + ".png");
        final Button submit = (Button) findViewById(R.id.submit);
        final Button addImage = (Button) findViewById(R.id.addImage);
        final EditText newSocialName = (EditText) findViewById(R.id.newSocialName);
        final EditText newSocialDate = (EditText) findViewById(R.id.newSocialDate);
        final EditText newSocialDescription = (EditText) findViewById(R.id.newSocialDescription);
        final ImageView img = (ImageView) findViewById(R.id.newSocialImage);
    }

    public void socialPage(){
        specificRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ((TextView) findViewById(R.id.eventNameScreen)).setText(dataSnapshot.child("event").getValue(String.class));
                ((TextView) findViewById(R.id.eventEmailScreen)).setText(dataSnapshot.child("email").getValue(String.class));
                ((TextView) findViewById(R.id.eventDateScreen)).setText(dataSnapshot.child("date").getValue(String.class));
                ((TextView) findViewById(R.id.eventDescriptionScreen)).setText(dataSnapshot.child("description").getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    public static void setImg(final String string, final View view, final int imgId){
        class DownloadFilesTask extends AsyncTask<Void, Void, Bitmap>{
            protected Bitmap doInBackground(Void... voids){
                return getBitmapFromURL(string);
            }
            protected void onPostExecute(Bitmap img){
                ((ImageView) view.findViewById(imgId)).setImageBitmap(img);
            }
        }

        FirebaseStorage.getInstance().getReference().child(string+".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<android.net.Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                new DownloadFilesTask().execute();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
}
