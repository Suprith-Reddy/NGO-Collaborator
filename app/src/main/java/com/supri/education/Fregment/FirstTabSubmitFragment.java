package com.supri.education.Fregment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.supri.education.FirstActivity;
import com.supri.education.Model.PostModel;
import com.supri.education.MyApplication;
import com.supri.education.R;

import java.util.ArrayList;
import java.util.List;

public class FirstTabSubmitFragment extends android.app.Fragment {


    private FirstActivity parent;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private EditText etReactContact;
    private EditText etReactAddress;
    private EditText etReactDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parent = (FirstActivity)getActivity();
        View view = inflater.inflate(R.layout.fregment_firsttab_submit, container, false);

        etReactDescription = (EditText)view.findViewById(R.id.etReactDescription);
        etReactAddress = (EditText)view.findViewById(R.id.etReactAddress);
        etReactContact = (EditText)view.findViewById(R.id.etReactContact);
        etReactDescription.setBackground(MyApplication.getRoundDrawable(parent, 1, Color.TRANSPARENT,  ContextCompat.getColor(parent, R.color.colorPrimaryDark), 1));
        etReactAddress.setBackground(MyApplication.getRoundDrawable(parent, 1, Color.TRANSPARENT,  ContextCompat.getColor(parent, R.color.colorPrimaryDark), 1));
        etReactContact.setBackground(MyApplication.getRoundDrawable(parent, 1, Color.TRANSPARENT,  ContextCompat.getColor(parent, R.color.colorPrimaryDark), 1));

        Button btnSubmit = (Button)view.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent.onSubmitReact(etReactDescription.getText().toString(), etReactAddress.getText().toString(), etReactContact.getText().toString());
            }
        });
        return view;
    }

}