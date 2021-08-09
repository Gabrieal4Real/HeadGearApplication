package com.example.headgear.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.headgear.Home.Homepage;
import com.example.headgear.Home.MainPage;
import com.example.headgear.Model.User;
import com.example.headgear.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class ProfilePage extends AppCompatActivity {

    private static String HEADGEAR_PREFS = "APP_PREFS";
    private static String ACTIVITY_TAG = ProfilePage.class.getSimpleName();
    int i;
    MeowBottomNavigation bottomNavigation2;
    private SharedPreferences sharedPreferences;
    private DatabaseReference dDatabase;
    private TextView name, username, password;
    private TextView summarytitle, depressionsummary, anxietysummary, stresssummary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        sharedPreferences = getSharedPreferences(HEADGEAR_PREFS, MODE_PRIVATE);
        dDatabase = FirebaseDatabase.getInstance().getReference("User");
        name = findViewById(R.id.Name);
        username = findViewById(R.id.UserID);
        password = findViewById(R.id.Password);

        summarytitle = findViewById(R.id.summarytitle);
        depressionsummary = findViewById(R.id.depressionsummary);
        anxietysummary = findViewById(R.id.anxietysummary);
        stresssummary = findViewById(R.id.stresssummary);

        if (sharedPreferences.contains("Summary Title21")) {
            summarytitle.setText(sharedPreferences.getString("Summary Title21", ""));
            depressionsummary.setText(sharedPreferences.getString("Depression Summary21", ""));
            anxietysummary.setText(sharedPreferences.getString("Anxiety Summary21", ""));
            stresssummary.setText(sharedPreferences.getString("Stress Summary21", ""));
        }

        if (sharedPreferences.contains("Summary Title42")) {
            summarytitle.setText(sharedPreferences.getString("Summary Title42", ""));
            depressionsummary.setText(sharedPreferences.getString("Depression Summary42", ""));
            anxietysummary.setText(sharedPreferences.getString("Anxiety Summary42", ""));
            stresssummary.setText(sharedPreferences.getString("Stress Summary42", ""));
        }


        Random random = new Random();
        i = random.nextInt(3); //will get a value from 0 - 3
        dDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User myUser = dataSnapshot.child(sharedPreferences.getString("User ID", ""))
                        .getValue(User.class);
                username.setText(sharedPreferences.getString("User ID", ""));
                password.setText(myUser.getPassword());

                if (i == 1) {
                    name.setText("Welcome Back, \n" + myUser.getName());
                } else if (i == 2) {
                    name.setText("You've Returned! \n" + myUser.getName());
                } else if (i == 3) {
                    name.setText("Hello Again, \n" + myUser.getName());
                } else {
                    name.setText("Hello There, \n" + myUser.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        bottomNavigation2 = findViewById(R.id.meowBottomNavigationprofile);
        bottomNavigation2.add(new MeowBottomNavigation.Model(1, R.drawable.ic_profile));
        bottomNavigation2.add(new MeowBottomNavigation.Model(2, R.drawable.ic_homepage));
        bottomNavigation2.add(new MeowBottomNavigation.Model(3, R.drawable.ic_logout));

        bottomNavigation2.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });

        bottomNavigation2.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {

                if (item.getId() == 1) {
                } else if (item.getId() == 2) {
                    openHomepage();
                } else {
                    Logout();
                }
            }
        });

        bottomNavigation2.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(), "You Are Already In The Profile Page",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void openHomepage() {
        Log.d(ACTIVITY_TAG, "HomeActivity Button is pressed");
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
        finish();
    }

    private void Logout() {
        Log.d(ACTIVITY_TAG, "Log out is pressed");
        Toast.makeText(getApplicationContext(), "Goodbye, We Hope To See You Again",
                Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(ProfilePage.this, MainPage.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        bottomNavigation2.show(1, true);
    }

}