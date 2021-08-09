package com.example.headgear.Aid.Stress;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.headgear.Adapter.AidStressAdapter;
import com.example.headgear.Aid.Aid.AidPage;
import com.example.headgear.Model.Aid;
import com.example.headgear.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class AidStressPage extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fab;
    List<AidStressSub> aidStressSubList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aid_stress_page);
        Toast.makeText(getApplicationContext(), "Refresh Page If Page Isn't Loaded",
                Toast.LENGTH_SHORT).show();
        fab = findViewById(R.id.refreshstress);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.removeAllViews();
                recyclerView.refreshDrawableState();
                RotateAnimation rotate =
                        new RotateAnimation(0, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(500);
                rotate.setFillAfter(true);
                fab.setAnimation(rotate);
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        initData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        AidStressAdapter aidStressAdapter = new AidStressAdapter(aidStressSubList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(aidStressAdapter);
    }

    private void initData() {
        aidStressSubList = new ArrayList<>();

        DatabaseReference databaseReference =
                FirebaseDatabase.getInstance().getReference("AidStress");
        Query GET_THE_PARTICULAR_AID_BREATHING = databaseReference.child("Breathing");
        Query GET_AID_DEFINITION = databaseReference.child("Definition");
        Query GET_AID_MANAGE_WORRIES = databaseReference.child("ManageWorries");
        Query GET_AID_METAPHORS = databaseReference.child("Metaphors");
        Query GET_AID_POSITIVE = databaseReference.child("PositiveSteps");
        Query GET_AID_THINKING_PATTERNS = databaseReference.child("ThinkingPatterns");

        GET_AID_DEFINITION.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Aid aid = dataSnapshot.getValue(Aid.class);
                aidStressSubList.add(new AidStressSub(aid.getTitle(), aid.getImage(), aid.getDescription()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        GET_THE_PARTICULAR_AID_BREATHING.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Aid aid = dataSnapshot.getValue(Aid.class);
                aidStressSubList.add(new AidStressSub(aid.getTitle(), aid.getImage(), aid.getDescription()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        GET_AID_MANAGE_WORRIES.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Aid aid = dataSnapshot.getValue(Aid.class);
                aidStressSubList.add(new AidStressSub(aid.getTitle(), aid.getImage(), aid.getDescription()));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        GET_AID_METAPHORS.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Aid aid = dataSnapshot.getValue(Aid.class);
                aidStressSubList.add(new AidStressSub(aid.getTitle(), aid.getImage(), aid.getDescription()));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        GET_AID_POSITIVE.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Aid aid = dataSnapshot.getValue(Aid.class);
                aidStressSubList.add(new AidStressSub(aid.getTitle(), aid.getImage(), aid.getDescription()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        GET_AID_THINKING_PATTERNS.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Aid aid = dataSnapshot.getValue(Aid.class);
                aidStressSubList.add(new AidStressSub(aid.getTitle(), aid.getImage(), aid.getDescription()));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onBackPressed() { //Copy to all folders
        super.onBackPressed();
        Intent intent = new Intent(this, AidPage.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slidlefttoright, R.anim.sliderighttoleft);
        finish();
    }
}