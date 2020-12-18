package com.supri.education;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kaopiz.kprogresshud.KProgressHUD;

public class MainActivity extends AppCompatActivity {

    private EditText etUserName;
    private EditText etPassword;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText)findViewById(R.id.etPassword);
        Button btnSignin = (Button)findViewById(R.id.btnSignin);
        Button btnRegister = (Button)findViewById(R.id.btnRegister);
        etUserName.setBackground(MyApplication.getRoundDrawable(MainActivity.this, 1, Color.TRANSPARENT,  ContextCompat.getColor(this, R.color.colorPrimaryDark), 1));
        etPassword.setBackground(MyApplication.getRoundDrawable(MainActivity.this, 1, Color.TRANSPARENT,  ContextCompat.getColor(this, R.color.colorPrimaryDark), 1));
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSingin();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRegister();
            }
        });
    }

    private void onSingin() {
        final String username = etUserName.getText().toString();
        final String password = etPassword.getText().toString();
        if (username.isEmpty()) {
            Toast.makeText(this, "Please input Username.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Please input Password.", Toast.LENGTH_SHORT).show();
            return;
        }
        final KProgressHUD hud = KProgressHUD.create(this).show();
        DatabaseReference ref = database.getReference().child("users");
        ref.orderByChild("username").equalTo(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hud.dismiss();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot val: dataSnapshot.getChildren()) {
                        if (val.child("password").getValue().equals(password)) {
                            MyApplication.username = username;
                            startActivity(new Intent(MainActivity.this, FirstActivity.class));
                        } else {
                            Toast.makeText(MainActivity.this, "Incorrect password.", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "There is not user.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hud.dismiss();
            }
        });
    }

    private void onRegister() {
        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
    }
}
