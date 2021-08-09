package com.example.headgear.Assessment.Assessment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.headgear.Assessment.DASS21.AssessmentDASS21Page;
import com.example.headgear.Assessment.DASS42.AssessmentDASS42Page;
import com.example.headgear.Home.Homepage;
import com.example.headgear.R;

public class AssessmentPage extends AppCompatActivity {

    private static String ACTIVITY_TAG = AssessmentDASS21Page.class.getSimpleName();
    private Button DASS_21, DASS_42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_page);

        DASS_21 = findViewById(R.id.DASS21);
        DASS_42 = findViewById(R.id.DASS42);

        MeowBottomNavigation bottomNavigation4 = findViewById(R.id.meowBottomNavigationassessment);
        bottomNavigation4.add(new MeowBottomNavigation.Model(1, R.drawable.ic_homepage));

        bottomNavigation4.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });

        bottomNavigation4.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                if (item.getId() == 1) {
                    onBackPressed();
                }
            }
        });

        bottomNavigation4.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
            }
        });


        DASS_21.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openAssessmentDASSPage21();
            }
        });
        DASS_42.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openAssessmentDASSPage42();
            }
        });
    }

    public void openAssessmentDASSPage21() {
        Log.d(ACTIVITY_TAG, "opening DASS21 Questions");
        Intent intent = new Intent(this, AssessmentDASS21Page.class);
        startActivity(intent);
        finish();
    }

    public void openAssessmentDASSPage42() {
        Log.d(ACTIVITY_TAG, "opening DASS42 Questions");
        Intent intent = new Intent(this, AssessmentDASS42Page.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() { //Copy to all folders
        super.onBackPressed();
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slidlefttoright, R.anim.sliderighttoleft);
        finish();
    }
}
