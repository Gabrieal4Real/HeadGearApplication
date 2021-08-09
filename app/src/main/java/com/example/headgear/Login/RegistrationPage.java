package com.example.headgear.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.headgear.Home.MainPage;
import com.example.headgear.Model.User;
import com.example.headgear.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RegistrationPage extends AppCompatActivity {
    private static String HEADGEAR_PREFS = "APP_PREFS";
    private static String ACTIVITY_TAG = RegistrationPage.class.getSimpleName();
    private Context context = RegistrationPage.this;

    private EditText Username, Name, Password, ConfirmPassword;
    private Button login, register;
    private SharedPreferences sharedPreferences;
    String username, name, password, confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        //Shared Preference enabled right here
        sharedPreferences = getSharedPreferences(HEADGEAR_PREFS, MODE_PRIVATE);

        Username = findViewById(R.id.usernameedit2);
        Name = findViewById(R.id.nameedit2);
        Password = findViewById(R.id.passwordedit2);
        ConfirmPassword = findViewById(R.id.confirmpasswordedit2);
        register = findViewById(R.id.completeregistration2);
        login = findViewById(R.id.login2);


        register.setOnClickListener(registerListener);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openLoginPage();
            }
        });


    }


    private View.OnClickListener registerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            username = Username.getText().toString();
            name = Name.getText().toString();
            password = Password.getText().toString();
            confirm_password = ConfirmPassword.getText().toString();

            if (sharedPreferences.contains("User ID") || sharedPreferences.contains("Guest")) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
            } else {
                if (username.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Type An Unique Username",
                            Toast.LENGTH_SHORT).show();
                    YoYo.with(Techniques.Shake).duration(1000).playOn(Username);

                }
                if (password.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Password Is Required To Successfully Register",
                            Toast.LENGTH_SHORT).show();
                    YoYo.with(Techniques.Shake).duration(1000).playOn(Password);

                }

                if (confirm_password.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Please Type The Password Again To Reconfirm",
                            Toast.LENGTH_SHORT).show();
                    YoYo.with(Techniques.Shake).duration(1000).playOn(ConfirmPassword);

                }

                if (name.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Full Name Is Required To Enjoy The Full Version Of This App",
                            Toast.LENGTH_SHORT).show();
                    YoYo.with(Techniques.Shake).duration(1000).playOn(Name);

                }
                if (!username.equals("") && !password.equals("") &&
                        !confirm_password.equals("") && !name.equals("")) {
                    if (password.contentEquals(confirm_password)) {
                        final DatabaseReference dDatabase = FirebaseDatabase.getInstance()
                                .getReference();
                        Query GET_AND_MATCH_USER = dDatabase.child("User");

                        GET_AND_MATCH_USER.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.child(username).exists()) {
                                    Toast.makeText(context, "The Username Is Already Taken",
                                            Toast.LENGTH_LONG).show();
                                    YoYo.with(Techniques.Shake).duration(1000).playOn(Username);
                                } else {
                                    User duser = new User(Name.getText().toString(),
                                            Password.getText().toString());
                                    dDatabase.child("User").child(Username.getText().toString())
                                            .setValue(duser)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    Toast.makeText(context,
                                                            "New Account Successfully Created",
                                                            Toast.LENGTH_SHORT).show();
                                                    Toast.makeText(context,
                                                            "Login To Authenticate",
                                                            Toast.LENGTH_SHORT).show();
                                                    openLoginPage();
                                                }
                                            });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.d(ACTIVITY_TAG, databaseError.getMessage() + "" +
                                        databaseError.getDetails());
                            }
                        });
                    } else {
                        Toast.makeText(context, "Password Aren't Matching, " +
                                        "Please Reenter Password",
                                Toast.LENGTH_LONG).show();
                        YoYo.with(Techniques.Shake).duration(1000).playOn(Password);
                        YoYo.with(Techniques.Shake).duration(1000).playOn(ConfirmPassword);
                        Password.setText("");
                        ConfirmPassword.setText("");
                    }
                }
            }
        }
    };

    public void openLoginPage() {
        Log.d(ACTIVITY_TAG, "Login page Opening");
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() { //Copy to all folders
        super.onBackPressed();
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slidlefttoright, R.anim.sliderighttoleft);
        finish();
    }
}

