package com.daniel.favour;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class swichify extends AppCompatActivity {
    ImageView add;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(
                R.layout.swicify
        );
        initView();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             addNew mapApp=new addNew();
                mapApp.show(getSupportFragmentManager(),"Swichy");
            }
        });
        changeStatusBar();
    }

    private void initView() {
        add=findViewById(R.id.addNew);
    }

    public void changeStatusBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
        }
    }

}
