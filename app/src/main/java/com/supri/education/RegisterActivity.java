package com.supri.education;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.supri.education.Model.UserModel;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Register User");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Register NGO");
        host.addTab(spec);

        // tab1
        final EditText etUserName = (EditText)findViewById(R.id.etUserName);
        final EditText etFullName = (EditText)findViewById(R.id.etFullName);
        final EditText etPassword = (EditText)findViewById(R.id.etPassword);
        final EditText etConfirmPassword = (EditText)findViewById(R.id.etConfirmPassword);
        final EditText etEmail = (EditText)findViewById(R.id.etEmail);
        final EditText etContact = (EditText)findViewById(R.id.etContact);
        Button btnRegister = (Button)findViewById(R.id.btnRegister);

        etUserName.setBackground(MyApplication.getRoundDrawable(RegisterActivity.this, 1, Color.TRANSPARENT,  ContextCompat.getColor(this, R.color.colorPrimaryDark), 1));
        etFullName.setBackground(MyApplication.getRoundDrawable(RegisterActivity.this, 1, Color.TRANSPARENT,  ContextCompat.getColor(this, R.color.colorPrimaryDark), 1));
        etPassword.setBackground(MyApplication.getRoundDrawable(RegisterActivity.this, 1, Color.TRANSPARENT,  ContextCompat.getColor(this, R.color.colorPrimaryDark), 1));
        etConfirmPassword.setBackground(MyApplication.getRoundDrawable(RegisterActivity.this, 1, Color.TRANSPARENT,  ContextCompat.getColor(this, R.color.colorPrimaryDark), 1));
        etEmail.setBackground(MyApplication.getRoundDrawable(RegisterActivity.this, 1, Color.TRANSPARENT,  ContextCompat.getColor(this, R.color.colorPrimaryDark), 1));
        etContact.setBackground(MyApplication.getRoundDrawable(RegisterActivity.this, 1, Color.TRANSPARENT,  ContextCompat.getColor(this, R.color.colorPrimaryDark), 1));

        final EditText etNGOName = (EditText)findViewById(R.id.etNGOName);
        final EditText etNGOType = (EditText)findViewById(R.id.etNGOType);
        final EditText etNGOUserName = (EditText)findViewById(R.id.etNGOUserName);
        final EditText etNGOPassword = (EditText)findViewById(R.id.etNGOPassword);
        final EditText etNGOConfirmPassword = (EditText)findViewById(R.id.etNGOConfirmPassword);
        final EditText etNGOEmail = (EditText)findViewById(R.id.etNGOEmail);
        final EditText etNGOContact = (EditText)findViewById(R.id.etNGOContact);
        Button btnNGORegister = (Button)findViewById(R.id.btnNGORegister);

        etNGOName.setBackground(MyApplication.getRoundDrawable(RegisterActivity.this, 1, Color.TRANSPARENT,  ContextCompat.getColor(this, R.color.colorPrimaryDark), 1));
        etNGOType.setBackground(MyApplication.getRoundDrawable(RegisterActivity.this, 1, Color.TRANSPARENT,  ContextCompat.getColor(this, R.color.colorPrimaryDark), 1));
        etNGOUserName.setBackground(MyApplication.getRoundDrawable(RegisterActivity.this, 1, Color.TRANSPARENT,  ContextCompat.getColor(this, R.color.colorPrimaryDark), 1));
        etNGOPassword.setBackground(MyApplication.getRoundDrawable(RegisterActivity.this, 1, Color.TRANSPARENT,  ContextCompat.getColor(this, R.color.colorPrimaryDark), 1));
        etNGOConfirmPassword.setBackground(MyApplication.getRoundDrawable(RegisterActivity.this, 1, Color.TRANSPARENT,  ContextCompat.getColor(this, R.color.colorPrimaryDark), 1));
        etNGOEmail.setBackground(MyApplication.getRoundDrawable(RegisterActivity.this, 1, Color.TRANSPARENT,  ContextCompat.getColor(this, R.color.colorPrimaryDark), 1));
        etNGOContact.setBackground(MyApplication.getRoundDrawable(RegisterActivity.this, 1, Color.TRANSPARENT,  ContextCompat.getColor(this, R.color.colorPrimaryDark), 1));

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterUser(0, "normal", etUserName.getText().toString(), etFullName.getText().toString(), etPassword.getText().toString(), etConfirmPassword.getText().toString(),
                        etEmail.getText().toString(), etContact.getText().toString());
            }
        });

        btnNGORegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterUser(1, etNGOType.getText().toString(), etNGOUserName.getText().toString(), etNGOName.getText().toString(), etNGOPassword.getText().toString(),
                        etNGOConfirmPassword.getText().toString(), etNGOEmail.getText().toString(), etNGOContact.getText().toString());
            }
        });
    }
    public void RegisterUser(final int flag, final String type, final String username, final String fullname, final String password, String confirm, final String email, final String contact) {
        if (flag == 1 && type.isEmpty()) {
            Toast.makeText(this, "Please input NGO Type.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (fullname.isEmpty()) {
            if (flag == 0) {
                Toast.makeText(this, "Please input Username.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please input NGO Name.", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        if (username.isEmpty()) {
            Toast.makeText(this, "Please Username.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Please input password.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(confirm)) {
            Toast.makeText(this, "Passwords don't match.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!MyApplication.isEmailValid(email)) {
            Toast.makeText(this, "Invalid Email.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (contact.isEmpty()) {
            Toast.makeText(this, "Please input contact.", Toast.LENGTH_SHORT).show();
            return;
        }

        final KProgressHUD hud = KProgressHUD.create(this).show();
        final DatabaseReference ref = database.getReference().child("users");
        ref.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hud.dismiss();
                if (dataSnapshot.exists()) {
                    onFaildRegister();
                } else {
                    UserModel user;
                    if (flag == 0) {
                        user = new UserModel("normal", type, fullname, username, password, email, contact);
                    } else {
                        user = new UserModel("NGO", type, fullname, username, password, email, contact);
                    }
                    ref.push().setValue(user);
                    MyApplication.username = user.getUsername();
                    startActivity(new Intent(RegisterActivity.this, FirstActivity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hud.dismiss();
            }
        });
    }
    public void onFaildRegister() {
        Toast.makeText(this, "Username is used.", Toast.LENGTH_SHORT).show();
    }
}
