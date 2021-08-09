package com.example.headgear.Assessment.DASS42;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.headgear.Assessment.Assessment.AssessmentPage;
import com.example.headgear.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AssessmentDASS42Page extends AppCompatActivity {
    private static String ACTIVITY_TAG = AssessmentDASS42Page.class.getSimpleName();
    int depressionpoints, anxietypoints, stresspoints;
    int ID = 1;
    int i = 1;
    private Button nextquestion;
    private DatabaseReference databaseReference;
    private RadioButton choice1, choice2, choice3, choice4;
    private TextView textView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        setContentView(R.layout.activity_assessment_dass_page);
        textView = findViewById(R.id.questiontext); //textview for data pulled from database
        nextquestion = findViewById(R.id.nextquestion); //Button Added for updating the question
        choice1 = findViewById(R.id.radioButton);
        choice2 = findViewById(R.id.radioButton2);
        choice3 = findViewById(R.id.radioButton3);
        choice4 = findViewById(R.id.radioButton4);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(42);
        progressBar.setProgress(0);
        nextQuestion();

    }

    //Need to Make it so when the button is pressed, the ID+1, if not the loop runs by itself
    private void nextQuestion() {
        Log.d(ACTIVITY_TAG, "Loading the questions from the database");
        Query Question = databaseReference.child("Questions for DASS-42").child("1");
        Question.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                textView.setText(dataSnapshot.child("QuestionNumber").getValue().toString());
                if (choice1.isChecked()) {
                    depressionpoints = depressionpoints + 0;
                } else if (choice2.isChecked()) {
                    depressionpoints = depressionpoints + 1;
                } else if (choice3.isChecked()) {
                    depressionpoints = depressionpoints + 2;
                } else if (choice4.isChecked()) {
                    depressionpoints = depressionpoints + 3;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(ACTIVITY_TAG, "Database Error");
            }
        });
        progressBar.setProgress(1);
        ID++;
        nextquestion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                YoYo.with(Techniques.SlideInRight).duration(500).playOn(textView);
                overridePendingTransition(R.anim.sliderighttoleft, R.anim.slidlefttoright);
                Query Question = databaseReference.child("Questions for DASS-42")
                        .child(String.valueOf(ID));
                if (ID >= 2 && ID <= 42) {
                    progressBar.setProgress(ID);
                    Question.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            textView.setText(dataSnapshot.child("QuestionNumber")
                                    .getValue().toString());
                            if (i <= 14) {
                                if (choice1.isChecked()) {
                                    depressionpoints = depressionpoints + 0;
                                    ID++;
                                    i++;
                                } else if (choice2.isChecked()) {
                                    depressionpoints = depressionpoints + 1;
                                    ID++;
                                    i++;
                                } else if (choice3.isChecked()) {
                                    depressionpoints = depressionpoints + 2;
                                    ID++;
                                    i++;
                                } else if (choice4.isChecked()) {
                                    depressionpoints = depressionpoints + 3;
                                    ID++;
                                    i++;
                                }
                            } else if (i >= 15 && i <= 28) {
                                if (choice1.isChecked()) {
                                    anxietypoints = anxietypoints + 0;
                                    ID++;
                                    i++;
                                } else if (choice2.isChecked()) {
                                    anxietypoints = anxietypoints + 1;
                                    ID++;
                                    i++;
                                } else if (choice3.isChecked()) {
                                    anxietypoints = anxietypoints + 2;
                                    ID++;
                                    i++;
                                } else if (choice4.isChecked()) {
                                    anxietypoints = anxietypoints + 3;
                                    ID++;
                                    i++;
                                }
                            } else if (i >= 29) {
                                if (choice1.isChecked()) {
                                    stresspoints = stresspoints + 0;
                                    ID++;
                                    i++;
                                } else if (choice2.isChecked()) {
                                    stresspoints = stresspoints + 1;
                                    ID++;
                                    i++;
                                } else if (choice3.isChecked()) {
                                    stresspoints = stresspoints + 2;
                                    ID++;
                                    i++;
                                } else if (choice4.isChecked()) {
                                    stresspoints = stresspoints + 3;
                                    ID++;
                                    i++;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d(ACTIVITY_TAG, "Database Error");
                        }
                    });
                } else if (ID == 43) {
                    if (choice1.isChecked()) {
                        stresspoints = stresspoints + 0;
                        ID++;
                        i++;
                    } else if (choice2.isChecked()) {
                        stresspoints = stresspoints + 1;
                        ID++;
                        i++;
                    } else if (choice3.isChecked()) {
                        stresspoints = stresspoints + 2;
                        ID++;
                        i++;
                    } else if (choice4.isChecked()) {
                        stresspoints = stresspoints + 3;
                        ID++;
                        i++;
                    }
                    openAssessmentReportPage();
                }
            }
        });

    }

    public void openAssessmentReportPage() {
        Log.d(ACTIVITY_TAG, "Opening Assessment Report Page and saving into database");
        String usertype = "";
        SharedPreferences sharedPreferences =
                getSharedPreferences("APP_PREFS", MODE_PRIVATE);

        if (sharedPreferences.contains("Guest")) {
            usertype = sharedPreferences.getString("Guest", "");
            Map<String, Object> update = new HashMap<>();
            update.put("AnxietyPoints", String.valueOf(anxietypoints));
            update.put("DepressionPoints", String.valueOf(depressionpoints));
            update.put("StressPoints", String.valueOf(stresspoints));

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("Results for DASS-42").child(usertype).updateChildren(update)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Intent intent = new Intent(AssessmentDASS42Page.this,
                                    AssessmentDASS42ReportPage.class);
                            startActivity(intent);
                            finish();
                        }
                    });

            //Cache command to make sure the data is shown
        } else if (sharedPreferences.contains("User ID")) {
            usertype = sharedPreferences.getString("User ID", "");
            Map<String, Object> update = new HashMap<>();
            update.put("AnxietyPoints", String.valueOf(anxietypoints));
            update.put("DepressionPoints", String.valueOf(depressionpoints));
            update.put("StressPoints", String.valueOf(stresspoints));

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("Results for DASS-42").child(usertype).updateChildren(update)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Intent intent = new Intent(AssessmentDASS42Page.this,
                                    AssessmentDASS42ReportPage.class);
                            startActivity(intent);
                            finish();
                        }
                    });
        }
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
