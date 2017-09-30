package com.example.kanyes.mdbsocials;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

/**
 * Created by Kanyes on 9/26/2017.
 */

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{
    Button signupButton, backToLogin;
    TextView emailView2, passwordView2;
    ImageView imageView2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signupButton = (Button) findViewById(R.id.signupButton2);
        backToLogin = (Button) findViewById(R.id.backToLogin);
        emailView2 = (TextView) findViewById(R.id.emailView2);
        passwordView2 = (TextView) findViewById(R.id.passwordView2);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        signupButton.setOnClickListener(this);
        backToLogin.setOnClickListener(this);
        animateButtons();


    }
    public void animateButtons() {
        // int[] imageButtonIds = {R.id.animateButton};
        int[] viewIds = {R.id.imageView2, R.id.emailView2, R.id.passwordView2, R.id.signupButton2,R.id.backToLogin};

        int i = 1;

        for (int viewId : viewIds) {
            // Button imageButton = (Button) findViewById(viewId);
            Animation fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fading_effect);
            fadeAnimation.setStartOffset(i * 250);
            //imageButton.startAnimation(fadeAnimation);

            int textViewId = viewIds[i-1];
            if (textViewId!=R.id.imageView2) {
                TextView textView = (TextView) findViewById(textViewId);
                textView.startAnimation(fadeAnimation);
            }
            else{
                ImageView textView = (ImageView) findViewById(textViewId);
                textView.startAnimation(fadeAnimation);
            }
            i ++;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.signupButton2):
                attemptSignup();
                break;
            case (R.id.backToLogin):
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }

    public void attemptSignup(){
        String email = ((EditText) findViewById(R.id.emailView2)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordView2)).getText().toString();
        LoginActivity.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Signup Successful!", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Signup failed!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            startActivity(new Intent(getApplicationContext(), FeedActivity.class));
                        }

                        // ...
                    }
                });
    }
    @Override
    public void onStart() {
        super.onStart();
        LoginActivity.auth.addAuthStateListener(LoginActivity.authListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (LoginActivity.authListener != null) {
            LoginActivity.auth.removeAuthStateListener(LoginActivity.authListener);
        }
    }
}
