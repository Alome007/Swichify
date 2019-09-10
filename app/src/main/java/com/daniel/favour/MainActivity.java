package com.daniel.favour;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker;
import com.treebo.internetavailabilitychecker.InternetConnectivityListener;

public class MainActivity extends AppCompatActivity implements InternetConnectivityListener {
TextView uptime;
int fromFireBase;
    InternetAvailabilityChecker mInternetAvailabilityChecker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        getFromFireBase("1234567890");
        InternetAvailabilityChecker.init(this);
    mInternetAvailabilityChecker = InternetAvailabilityChecker.getInstance();
        mInternetAvailabilityChecker.addInternetConnectivityListener(this);
        uptime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,EsptouchDemoActivity.class));
            }
        });

        changeStatusBarColor();
        //todo uncomment
      //  if (checkDevice()){
       //     setContentView(R.layout.activity_main);
            initViews();
         //   setUptime(uptime,86400 );
//            SharedPreferences sharedPreferences=getSharedPreferences("Devices", Context.MODE_PRIVATE);
//            String id=sharedPreferences.getString("DeviceId",null);
            fight("1234567890");
        //    setUptime(uptime,getFromFireBase(""));
           // Toast.makeText(MainActivity.this,"Purchased",Toast.LENGTH_LONG).show();
//        }else{
//            modalAppPri modalAppPri=new modalAppPri();
//            modalAppPri.show(getSupportFragmentManager(),"Not Registered");
//            setContentView(R.layout.emp);
//            changeStatusBarColor2();
        }
//todo uncomment
//        ImageView image=findViewById(R.id.mainImage);
        //todo change string to shareprefences value...
      //  checkDeviceStatus("1234567890",image);


    private void changeStatusBarColor2() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void initViews() {
        uptime=findViewById(R.id.uptime);
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
        }

    }
//    public boolean checkDevice(){
//        boolean yes;
//        SharedPreferences sharedPreferences=getSharedPreferences("device",MODE_PRIVATE);
//        String deviceV=sharedPreferences.getString("isDevice",null);
//        if (deviceV.equalsIgnoreCase("ok"))
//            yes=true;
//        else{
//            yes=false;
//        }
//        return yes;
//    }
    @SuppressLint("DefaultLocale")
    public void setUptime(TextView time, int total){
        int hour,min,sec;
        hour=total/3600;
        min=(total%3600)/60;
        sec=total%60;
        time.setText(String.format("%02d: %02d : %02d",hour,min,sec));
    }
    public void checkDeviceStatus(String deviceId, final ImageView imageView){
        DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Device").child(deviceId).child("status");
        db.addValueEventListener(new ValueEventListener() {
            @SuppressLint({"SetTextI18n", "ResourceAsColor"})
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String status=dataSnapshot.getValue().toString();
                if (status.isEmpty()){
                    imageView.setImageResource(R.drawable.offline);
                }
                else
                {
                    imageView.setImageResource(R.drawable.cirlce);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Alomeeeee", "loadPost:onCancelled", databaseError.toException());

            }

        });
    }
    public void fight(final String deviceId){
        final Handler handler = new Handler();
        final int delay = 1000;
        handler.postDelayed(new Runnable(){
            @SuppressLint("DefaultLocale")
            public void run()
            {
                DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Device").child(deviceId).child("status");
                db.removeValue();
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    public void getFromFireBase(String  deviceId) {
        DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Device").child(deviceId).child("upTime");
        db.addValueEventListener(new ValueEventListener() {
            @SuppressLint({"SetTextI18n", "ResourceAsColor"})
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               fromFireBase=Integer.parseInt(dataSnapshot.getValue().toString());
                Log.d("Favourite", "onDataChange() returned: " + fromFireBase);
                setUptime(uptime,fromFireBase);

             //   Toast.makeText(MainActivity.this, dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.w("Alomeeeee", "loadPost:onCancelled", databaseError.toException());

        }

        });

    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        if (!isConnected){
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
            ImageView image=findViewById(R.id.mainImage);
            image.setImageResource(R.drawable.offline);
        }
    }

    @Override
    protected void onDestroy() {
        mInternetAvailabilityChecker.removeInternetConnectivityChangeListener(this);
        super.onDestroy();
    }
}
