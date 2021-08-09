package com.example.headgear.Home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.headgear.R;

public class SplashScreenPage extends AppCompatActivity {
    private static int Splash_time = 1500;
    private TextView title, header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_page);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splashscreen = new Intent(SplashScreenPage.this, MainPage.class);
                startActivity(splashscreen);
                finish();
            }
        }, Splash_time);

        title = findViewById(R.id.splashscreentitle);
        header = findViewById(R.id.splashscreenheader);

        YoYo.with(Techniques.FadeIn).duration(1200).playOn(title);
        YoYo.with(Techniques.FadeInDown).duration(1200).playOn(header);
    }
}