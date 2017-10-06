package com.example.kanyes.mdbsocials;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

/**
 * Created by Kanyes on 9/29/2017.
 */

public class SocialPage extends AppCompatActivity implements View.OnClickListener {
    Button interested, back;
    TextView event, email, date, description;
    ImageView image;
    DatabaseReference newRef;
    Intent i;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_page);
        i = getIntent();
        newRef = FirebaseDatabase.getInstance().getReference("/socials/"+i.getStringExtra("id"));

        event = (TextView) findViewById(R.id.eventNameScreen);
        email = (TextView) findViewById(R.id.eventEmailScreen);
        date = (TextView) findViewById(R.id.eventDateScreen);
        description = (TextView) findViewById(R.id.eventDescriptionScreen);
        interested = (Button) findViewById(R.id.interested);
        back = (Button) findViewById(R.id.back);
        newRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                event.setText(dataSnapshot.child("event").getValue(String.class));
                email.setText(dataSnapshot.child("email").getValue(String.class));
                date.setText(dataSnapshot.child("date").getValue(String.class));
                description.setText(dataSnapshot.child("description").getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        interested.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.interested):
                newRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        newRef.child("interested").setValue(Integer.toString(Integer.parseInt(dataSnapshot.child("interested").getValue(String.class)) + 1));
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                break;
            case (R.id.back):
                startActivity(new Intent(getApplicationContext(), FeedActivity.class));
        }
    }
}
