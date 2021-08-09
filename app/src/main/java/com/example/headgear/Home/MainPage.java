package com.example.headgear.Home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.headgear.Login.LoginPage;
import com.example.headgear.Login.RegistrationPage;
import com.example.headgear.Model.User;
import com.example.headgear.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainPage extends AppCompatActivity {


    private static String ACTIVITY_TAG = MainPage.class.getSimpleName();
    //Prefs and Tags
    private static String HEADGEAR_PREFS = "APP_PREFS";
    private SharedPreferences sharedPreferences;
    private DatabaseReference dDatabase;
    private Button guest, login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sharedPreferences = getSharedPreferences(HEADGEAR_PREFS, MODE_PRIVATE);
        dDatabase = FirebaseDatabase.getInstance().getReference("User");
        guest = findViewById(R.id.guest);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        guest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openLoginAsGuest();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openLoginPage();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openRegistrationPage();
            }
        });

        openHomepage();
    }

    public void openLoginAsGuest() {
        dDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //We check whether there is the same username in the database
                if (dataSnapshot.child(guest.getText().toString()).exists()) {
                    Log.d(ACTIVITY_TAG, "Login as Guest is clicked");
                    User myUser = dataSnapshot.child(guest.getText().toString())
                            .getValue(User.class);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.commit();
                    editor.putString("Guest", guest.getText().toString());
                    editor.apply();

                    Toast.makeText(getApplicationContext(),
                            "Welcome " + myUser.getName(),
                            Toast.LENGTH_SHORT).show();
                    openHomepage();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Login As Guest Is Unsuccessful",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(ACTIVITY_TAG, "Database Error");
            }
        });
    }

    public void openHomepage() {
        Log.d(ACTIVITY_TAG, "HomeActivity Button is pressed");
        if (sharedPreferences.contains("Guest") || sharedPreferences.contains("User ID")) {
            Intent intent = new Intent(this, Homepage.class);
            startActivity(intent);
            finish();
        }
    }

    public void openLoginPage() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        Log.d(ACTIVITY_TAG, "Login Button is pressed");
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
        finish();
    }

    public void openRegistrationPage() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Log.d(ACTIVITY_TAG, "Register Button is pressed");
        Intent intent = new Intent(this, RegistrationPage.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(ACTIVITY_TAG, "Back button is touched");
        overridePendingTransition(R.anim.slidlefttoright, R.anim.sliderighttoleft);
        finishAffinity();
        finish();
    }
}
