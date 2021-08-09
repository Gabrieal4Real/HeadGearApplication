package com.example.headgear.Home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.headgear.Aid.Aid.AidPage;
import com.example.headgear.Assessment.Assessment.AssessmentPage;
import com.example.headgear.Forum.ForumPostPage;
import com.example.headgear.Login.ProfilePage;
import com.example.headgear.R;

public class Homepage extends AppCompatActivity {
    private static String ACTIVITY_TAG = Homepage.class.getSimpleName();
    //Prefs and Tags
    private static String HEADGEAR_PREFS = "APP_PREFS";
    MeowBottomNavigation bottomNavigation;
    private SharedPreferences sharedPreferences;
    private Button assessment, aid, forum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);


        bottomNavigation = findViewById(R.id.meowBottomNavigationhome);
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_profile));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_homepage));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_logout));

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
            }
        });

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {

                if (item.getId() == 1) {
                    if (sharedPreferences.contains("Guest")) {
                        Toast.makeText(getApplicationContext(), "You Need To Be " +
                                        "Registered To View Profile",
                                Toast.LENGTH_SHORT).show();


                    } else
                        openProfile();
                } else if (item.getId() == 2) {
                } else if (item.getId() == 3) {
                    Logout();
                }
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(), "You Are Already In The Homepage",
                        Toast.LENGTH_SHORT).show();
            }
        });


        sharedPreferences = getSharedPreferences(HEADGEAR_PREFS, MODE_PRIVATE);
        assessment = findViewById(R.id.Assessment);
        aid = findViewById(R.id.aid);
        forum = findViewById(R.id.forum);

        if (sharedPreferences.contains("Guest")) {
            Toast.makeText(getApplicationContext(), "Forum Is Disabled," +
                            " Please Logout and Login As Registered User",
                    Toast.LENGTH_SHORT).show();
            forum.setEnabled(false);
            forum.setTextColor(Color.parseColor("#42000000"));
        } else if (sharedPreferences.contains("User ID")) {
            forum.setEnabled(true);
            forum.setTextColor(Color.parseColor("#FFFFFF"));
        }

        assessment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openAssessmentActivity();
            }
        });

        aid.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openAidActivity();
            }
        });

        forum.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openForumActivity();
            }
        });

    }

    private void Logout() {
        Log.d(ACTIVITY_TAG, "Log out is pressed");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Toast.makeText(getApplicationContext(), "Goodbye, We Hope To See You Again",
                Toast.LENGTH_SHORT).show();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(Homepage.this, MainPage.class);
        startActivity(intent);
        finish();
    }
    private void openProfile() {
        Log.d(ACTIVITY_TAG, "Opening Profile Activity");
        Intent intent = new Intent(this, ProfilePage.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slidlefttoright, R.anim.sliderighttoleft);
        finish();
    }

    private void openAssessmentActivity() {
        Log.d(ACTIVITY_TAG, "Opening Assessment Activity");
        Intent intent = new Intent(this, AssessmentPage.class);
        startActivity(intent);
        finish();
    }

    private void openAidActivity() {
        Log.d(ACTIVITY_TAG, "Opening Aid Activity");
        Intent intent = new Intent(this, AidPage.class);
        startActivity(intent);
        finish();
    }


    private void openForumActivity() {
        Log.d(ACTIVITY_TAG, "Opening Forum Activity");
        Intent intent = new Intent(this, ForumPostPage.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        bottomNavigation.show(2, true);
    }

    @Override
    public void onBackPressed() { //Copy to all folders
        super.onBackPressed();
        Logout();
    }
}
