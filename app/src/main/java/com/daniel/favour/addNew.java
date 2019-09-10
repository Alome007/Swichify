package com.daniel.favour;
 /*Created by Alome on 2/16/2019.
 WeMet
 */
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
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

public class addNew extends BottomSheetDialogFragment {
    EditText title,value;
    View view;


    @SuppressLint({"RestrictedApi", "InflateParams"})
    public void setupDialog(final Dialog dialog, final int style) {
        super.setupDialog(dialog, style);
        view = LayoutInflater.from(getContext()).inflate(R.layout.add_pri, null);
        dialog.setContentView(view);
       // initViews();
        setCancelable(false);
        ((View) view.getParent()).getLayoutParams();
        BottomSheetBehavior.from(dialog.findViewById(R.id.design_bottom_sheet)).setPeekHeight(10000);
        ((View) view.getParent()).setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));


    }
}

