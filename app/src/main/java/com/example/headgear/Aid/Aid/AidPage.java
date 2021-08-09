package com.example.headgear.Aid.Aid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.headgear.Aid.Anxiety.AidAnxietyPage;
import com.example.headgear.Aid.Depression.AidDepressionPage;
import com.example.headgear.Aid.Stress.AidStressPage;
import com.example.headgear.Assessment.DASS21.AssessmentDASS21Page;
import com.example.headgear.Home.Homepage;
import com.example.headgear.R;

public class AidPage extends AppCompatActivity {

    private static String ACTIVITY_TAG = AssessmentDASS21Page.class.getSimpleName();
    private Button depression, anxiety, stress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aid_page);

        depression = findViewById(R.id.depression);
        anxiety = findViewById(R.id.anxiety);
        stress = findViewById(R.id.stress);

        MeowBottomNavigation bottomNavigation3 = findViewById(R.id.meowBottomNavigationaid);
        bottomNavigation3.add(new MeowBottomNavigation.Model(1, R.drawable.ic_homepage));

        bottomNavigation3.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });

        bottomNavigation3.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                if (item.getId() == 1) {
                    onBackPressed();
                }
            }
        });

        bottomNavigation3.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
            }
        });


        depression.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openAidDepressionPage();
            }
        });

        anxiety.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openAidAnxietyPage();
            }
        });

        stress.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openAidStressPage();
            }
        });
    }

    public void openAidDepressionPage() {
        Log.d(ACTIVITY_TAG, "opening Depression Aid Page");
        Intent intent = new Intent(this, AidDepressionPage.class);
        startActivity(intent);
        finish();
    }

    public void openAidAnxietyPage() {
        Log.d(ACTIVITY_TAG, "opening Anxiety Aid Page");
        Intent intent = new Intent(this, AidAnxietyPage.class);
        startActivity(intent);
        finish();
    }

    public void openAidStressPage() {
        Log.d(ACTIVITY_TAG, "opening Stress Aid Page");
        Intent intent = new Intent(this, AidStressPage.class);
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
