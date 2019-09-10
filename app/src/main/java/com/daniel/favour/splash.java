package com.daniel.favour;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by alome daniel on 10/01/2019, been thursday
 */




public class splash extends AppCompatActivity {
    TextView urbler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        SharedPreferences sharedPreferences = getSharedPreferences("device", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("isDevice", "lol");
        editor.apply();
        changeStatusBarColor();
        urbler = findViewById(R.id.textView2);
        final Animation in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(1000);
        urbler.startAnimation(in);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                final Animation out = new AlphaAnimation(1.0f, 0.0f);
                out.setDuration(1000);
                urbler.startAnimation(out);
                Intent i = new Intent(splash.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 1000);

    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
        }

    }
}

