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
import com.example.headgear.Home.Homepage;
import com.example.headgear.Home.MainPage;
import com.example.headgear.Model.User;
import com.example.headgear.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity {
    private static String ACTIVITY_TAG = LoginPage.class.getSimpleName();
    //Prefs and Tags
    private static String HEADGEAR_PREFS = "APP_PREFS";
    private DatabaseReference dDatabase;
    private EditText usernameedit, passwordedit;
    private SharedPreferences sharedPreferences;
    private Button login, register;
    private Context dContext = LoginPage.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //Shared Preference enabled right here
        sharedPreferences = getSharedPreferences(HEADGEAR_PREFS, MODE_PRIVATE);

        //Database instance declared here
        dDatabase = FirebaseDatabase.getInstance().getReference("User");
        usernameedit = findViewById(R.id.usernameedit);
        passwordedit = findViewById(R.id.passwordedit);
        login = findViewById(R.id.login3);
        register = findViewById(R.id.register3);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (usernameedit.getText().toString().equals("") ||
                        passwordedit.getText().toString().equals("")) {
                    if (usernameedit.getText().toString().equals("") &&
                            passwordedit.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(),
                                "Username and Password is Required to Login",
                                Toast.LENGTH_SHORT).show();
                        YoYo.with(Techniques.Shake).duration(1000).playOn(usernameedit);
                        YoYo.with(Techniques.Shake).duration(1000).playOn(passwordedit);
                    } else if (usernameedit.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Username Cannot Be Blank ",
                                Toast.LENGTH_SHORT).show();
                        YoYo.with(Techniques.Shake).duration(1000).playOn(usernameedit);

                    } else if (passwordedit.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Please Insert Your Password",
                                Toast.LENGTH_SHORT).show();
                        YoYo.with(Techniques.Shake).duration(1000).playOn(passwordedit);
                    }
                }

                if (!usernameedit.getText().toString().equals("") &&
                        !passwordedit.getText().toString().equals("")) {
                    dDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //We check whether there is the same username in the database
                            if (dataSnapshot.child(usernameedit.getText().toString()).exists()) {
                                User myUser = dataSnapshot.child(usernameedit.getText().toString())
                                        .getValue(User.class);
                                //Check id password matches
                                if (myUser.getPassword().equals(passwordedit
                                        .getText().toString())) {
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.clear();
                                    editor.putString("User ID", usernameedit.getText().toString());
                                    editor.commit();
                                    Toast.makeText(getApplicationContext(),
                                            "Welcome " + myUser.getName(),
                                            Toast.LENGTH_SHORT).show();
                                    openHomepage();
                                } else {
                                    Toast.makeText(getApplicationContext(),
                                            "Login Is Unsuccessful, Please Reenter Password",
                                            Toast.LENGTH_SHORT).show();
                                    passwordedit.setText("");
                                    YoYo.with(Techniques.Shake).duration(1000).playOn(passwordedit);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Account Doesn't Exist, Please Register An Account",
                                        Toast.LENGTH_SHORT).show();
                                usernameedit.setText("");
                                passwordedit.setText("");
                                YoYo.with(Techniques.Shake).duration(1000).playOn(passwordedit);
                                YoYo.with(Techniques.Shake).duration(1000).playOn(usernameedit);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d(ACTIVITY_TAG, "Database Error");
                        }
                    });
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openRegistrationPage();
            }
        });
    }

    public void openHomepage() {
        Log.d(ACTIVITY_TAG, "HomeActivity Button Error");
        Intent intent = new Intent(dContext, Homepage.class);
        startActivity(intent);
        finish();
    }

    public void openRegistrationPage() {
        Log.d(ACTIVITY_TAG, "Registration Button Error");
        Intent intent = new Intent(dContext, RegistrationPage.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() { //Copy to all folders
        super.onBackPressed();
        Intent intent = new Intent(dContext, MainPage.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slidlefttoright, R.anim.sliderighttoleft);
        finish();
    }

}
