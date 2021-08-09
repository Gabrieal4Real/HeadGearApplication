package com.example.headgear.Assessment.DASS21;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.headgear.Assessment.Assessment.AssessmentPage;
import com.example.headgear.Model.DASS21Results;
import com.example.headgear.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AssessmentDASS21ReportPage extends AppCompatActivity {
    private static String ACTIVITY_TAG = AssessmentDASS21ReportPage.class.getSimpleName();
    private static String HEADGEAR_PREFS = "APP_PREFS";
    private TextView depressiontext, anxietytext, stresstext;
    private TextView depressionsummary, anxietysummary, stresssummary;
    private int depressionsum, anxietysum, stresssum;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_dass_report_page);

        depressiontext = findViewById(R.id.DepressionPoints);
        anxietytext = findViewById(R.id.AnxietyPoints);
        stresstext = findViewById(R.id.StressPoints);
        depressionsummary = findViewById((R.id.DepressionSummary));
        anxietysummary = findViewById((R.id.AnxietySummary));
        stresssummary = findViewById((R.id.StressSummary));
        sharedPreferences = getSharedPreferences(HEADGEAR_PREFS, MODE_PRIVATE);
        onTableForm();
    }

    private void onTableForm() {
        Log.d(ACTIVITY_TAG, "Loading the report from the database");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        SharedPreferences sharedPreferences =
                getSharedPreferences("APP_PREFS", MODE_PRIVATE);

        if (sharedPreferences.contains("User ID")) {
            Log.d(ACTIVITY_TAG, "Get data from firebase");
            Query GET_REGISTERED_USER_REPORT = databaseReference.child("Results for DASS-21")
                    .child(sharedPreferences.getString("User ID", ""));

            GET_REGISTERED_USER_REPORT.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    DASS21Results dass21Results = dataSnapshot.getValue(DASS21Results.class);
                    depressiontext.setText(dass21Results.getDepressionPoints());
                    anxietytext.setText(dass21Results.getAnxietyPoints());
                    stresstext.setText(dass21Results.getStressPoints());

                    depressionsum = Integer.parseInt(dass21Results.getDepressionPoints());
                    anxietysum = Integer.parseInt(dass21Results.getAnxietyPoints());
                    stresssum = Integer.parseInt(dass21Results.getStressPoints());
                    Summary(depressionsum, anxietysum, stresssum);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d(ACTIVITY_TAG, databaseError.getMessage() + " "
                            + databaseError.getDetails());
                }
            });
        } else if (sharedPreferences.contains("Guest")) {
            Query GET_GUEST_REPORT = databaseReference.child("Results for DASS-21")
                    .child(sharedPreferences.getString("Guest", ""));

            GET_GUEST_REPORT.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    DASS21Results results = dataSnapshot.getValue(DASS21Results.class);

                    depressiontext.setText(results.getDepressionPoints());
                    anxietytext.setText(results.getAnxietyPoints());
                    stresstext.setText(results.getStressPoints());
                    depressionsum = Integer.parseInt(results.getDepressionPoints());
                    anxietysum = Integer.parseInt(results.getAnxietyPoints());
                    stresssum = Integer.parseInt(results.getStressPoints());
                    Summary(depressionsum, anxietysum, stresssum);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d(ACTIVITY_TAG, databaseError.getMessage() + " "
                            + databaseError.getDetails());
                }
            });
        }
    }

    private void Summary(int a, int b, int c) {

        if (a <= 9) {
            depressionsummary.setText("⦾ " + "You have normal amounts of depression,");
        } else if (a <= 13) {
            depressionsummary.setText("⦾ " + "You have mild amounts of depression,");
        } else if (a <= 20) {
            depressionsummary.setText("⦾ " + "You have moderate amounts of depression,");
        } else if (a <= 27) {
            depressionsummary.setText("⦾ " + "You have severe amounts of depression,");
        } else {
            depressionsummary.setText("⦾ " + "You have extremely severe amounts of depression,");
        }

        if (b <= 7) {
            anxietysummary.setText("⦾ " + "You have normal amounts of anxiety,");
        } else if (b <= 9) {
            anxietysummary.setText("⦾ " + "You have mild amounts of anxiety,");
        } else if (b <= 14) {
            anxietysummary.setText("⦾ " + "You have moderate amounts of anxiety,");
        } else if (b <= 19) {
            anxietysummary.setText("⦾ " + "You have severe amounts of anxiety,");
        } else {
            anxietysummary.setText("⦾ " + "You have extremely severe amounts of anxiety,");
        }

        if (c <= 14) {
            stresssummary.setText("⦾ " + "You have normal amounts of stress.");
        } else if (c <= 18) {
            stresssummary.setText("⦾ " + "You have mild amounts of stress.");
        } else if (c <= 25) {
            stresssummary.setText("⦾ " + "You have moderate amounts of stress.");
        } else if (c <= 33) {
            stresssummary.setText("⦾ " + "You have severe amounts of stress.");
        } else {
            stresssummary.setText("⦾ " + "You have extremely severe amounts of stress.");
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Summary Title21", "Dass 21 Results");
        editor.putString("Depression Summary21", depressionsummary.getText().toString());
        editor.putString("Anxiety Summary21", anxietysummary.getText().toString());
        editor.putString("Stress Summary21", stresssummary.getText().toString());
        editor.apply();
    }

    @Override
    public void onBackPressed() { //Copy to all folders
        super.onBackPressed();
        Intent intent = new Intent(this, AssessmentPage.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slidlefttoright, R.anim.sliderighttoleft);
        finish();
    }
}
