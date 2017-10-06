package com.example.kanyes.mdbsocials;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Kanyes on 9/26/2017.
 */

public class FeedActivity extends AppCompatActivity implements View.OnClickListener {
    static ArrayList<Social> socials = new ArrayList<>();
    FeedAdapter adapter = new FeedAdapter(this, socials);
    FloatingActionButton fab;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/socials");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        final RecyclerView recyclerAdapter = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerAdapter.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter.setAdapter(adapter);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                socials = new ArrayList<>();
                for (DataSnapshot dataSnapshot2: dataSnapshot.getChildren()) {
                    socials.add(0, new Social(dataSnapshot2.child("email").getValue(String.class), dataSnapshot2.child("event").getValue(String.class), dataSnapshot2.getKey(), dataSnapshot2.child("interested").getValue(String.class), dataSnapshot2.child("description").getValue(String.class), dataSnapshot2.child("date").getValue(String.class)));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Value Failed", "Failed to read value.", error.toException());
            }
        });


        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.floatingActionButton):
                startActivity(new Intent(getApplicationContext(),NewSocialActivity.class));
        }

    }
}
