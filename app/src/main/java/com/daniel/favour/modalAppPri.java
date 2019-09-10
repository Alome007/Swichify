package com.daniel.favour;
 /*Created by Alome on 2/16/2019.
 WeMet
 */
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tooltip.Tooltip;

public class modalAppPri extends BottomSheetDialogFragment {
    EditText title,value;
    View view;
    EditText editText;
    ProgressDialog progressDialog;
    private Button cart;

    @SuppressLint({"RestrictedApi", "InflateParams"})
    public void setupDialog(final Dialog dialog, final int style) {
        super.setupDialog(dialog, style);
       view= LayoutInflater.from(getContext()).inflate(R.layout.add_pri, null);
        dialog.setContentView(view);
        initViews();
        setCancelable(false);
        ((View) view.getParent()).getLayoutParams();
        BottomSheetBehavior.from(dialog.findViewById(R.id.design_bottom_sheet)).setPeekHeight(10000);
        ((View) view.getParent()).setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        TextWatcher textWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editText.length()==10){
                   // checkFromFireBase();
//                    Toast.makeText(getContext(),"Run Fuction Before",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editText.length()==10){
                    checkFromFireBase();
                 //   Toast.makeText(getContext(),"Run Fuction Live",Toast.LENGTH_LONG).show();
                }
                if (editText.length()>=5||editText.length()<=5){
                    cart.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editText.length()==10){
                  //  checkFromFireBase();
                    // Toast.makeText(getContext(),"Run Fuction After",Toast.LENGTH_LONG).show();
                }
            }
        };
        editText.addTextChangedListener(textWatcher);
    }

    private void checkFromFireBase() {
        final DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Devices").child(editText.getText().toString());
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) {
                    Toast toast=Toast.makeText(getContext(),"Sorry your device does not exit",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                    enabledCart();
                }
                else {
                    SharedPreferences sharedPreferences=getContext().getSharedPreferences("Devices", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("DeviceId",editText.getText().toString());
                    editor.apply();
                    StartDeviceSetUp();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(),databaseError.toException().toString(),Toast.LENGTH_LONG).show();
                Log.d("Urbler:dbError", databaseError.getMessage());
                //Don't ignore errors!
            }
        };
        db.addListenerForSingleValueEvent(eventListener);


    }

    private void StartDeviceSetUp() {
        //todo call esp8266 configuratio n setUp....
        dismiss();
        startActivity(new Intent(getContext(),EsptouchDemoActivity.class));
    }

    private void enabledCart() {
        cart.setVisibility(View.VISIBLE);
    }


    private void initViews() {
        cart=view.findViewById(R.id.cart);
        editText=view.findViewById(R.id.edit);
    }


}

