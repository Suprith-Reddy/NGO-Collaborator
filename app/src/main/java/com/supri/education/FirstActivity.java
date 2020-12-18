package com.supri.education;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.supri.education.Fregment.FirstTabListFragment;
import com.supri.education.Fregment.FirstTabSubmitFragment;
import com.supri.education.Fregment.ThirdTabMyPostFragment;
import com.supri.education.Fregment.ThirdTabViewReactFragment;
import com.supri.education.Model.PostModel;
import com.supri.education.Model.ReactModel;

public class FirstActivity extends AppCompatActivity {

    public static String authID;
    public static String postID;
    public static String postDescription;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private EditText etDescription;
    private EditText etAddress;
    private EditText etContact;
    private FrameLayout flTab1Frame;

    FirstTabListFragment firstTabListFragment;
    FirstTabSubmitFragment firstTabSubmitFragment;
    ThirdTabMyPostFragment thirdTabMyPostFragment;
    ThirdTabViewReactFragment thirdTabViewReactFragment;
    private android.app.FragmentManager mFirstFragmentManager;
    private android.app.FragmentManager mThirdFragmentManager;
    private FrameLayout flTab3Frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("View Posts");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Add Post");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Your Posts");
        host.addTab(spec);

        //tab2
        etDescription = (EditText)findViewById(R.id.etDescription);
        etAddress = (EditText)findViewById(R.id.etAddress);
        etContact = (EditText)findViewById(R.id.etContact);
        etDescription.setBackground(MyApplication.getRoundDrawable(FirstActivity.this, 1, Color.TRANSPARENT,  ContextCompat.getColor(this, R.color.colorPrimaryDark), 1));
        etAddress.setBackground(MyApplication.getRoundDrawable(FirstActivity.this, 1, Color.TRANSPARENT,  ContextCompat.getColor(this, R.color.colorPrimaryDark), 1));
        etContact.setBackground(MyApplication.getRoundDrawable(FirstActivity.this, 1, Color.TRANSPARENT,  ContextCompat.getColor(this, R.color.colorPrimaryDark), 1));

        Button btnAdd = (Button)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddPost(etDescription.getText().toString(), etAddress.getText().toString(), etContact.getText().toString());
            }
        });

        //tab1
        flTab1Frame = (FrameLayout)findViewById(R.id.flTab1Frame);
        mFirstFragmentManager = getFragmentManager();
        firstTabListFragment = new FirstTabListFragment();
        android.app.FragmentTransaction fragmentTransaction = mFirstFragmentManager.beginTransaction();
        fragmentTransaction.replace(flTab1Frame.getId(), firstTabListFragment)
                .addToBackStack("tab1")
                .commit();

        //tab3
        flTab3Frame = (FrameLayout)findViewById(R.id.flTab3Frame);
        mThirdFragmentManager = getFragmentManager();
        thirdTabMyPostFragment = new ThirdTabMyPostFragment();
        fragmentTransaction = mThirdFragmentManager.beginTransaction();
        fragmentTransaction.replace(flTab3Frame.getId(), thirdTabMyPostFragment)
                .addToBackStack("tab3")
                .commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int firstTabCount = mFirstFragmentManager.getBackStackEntryCount();
        int thirdTabCount= mThirdFragmentManager.getBackStackEntryCount();
        if (firstTabCount == 1|| thirdTabCount == 1) finish();
    }

    public void onAddPost(String description, String address, String contact) {
        if (description.isEmpty()) {
            Toast.makeText(this, "Please input Description.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (address.isEmpty()) {
            Toast.makeText(this, "Please input Address.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (contact.isEmpty()) {
            Toast.makeText(this, "Please input Contact.", Toast.LENGTH_SHORT).show();
            return;
        }
        PostModel post = new PostModel(MyApplication.username, description, address, contact);
        DatabaseReference ref = database.getReference().child("posts");
        ref.push().setValue(post);
        Toast.makeText(this, "Success.", Toast.LENGTH_SHORT).show();
        etDescription.setText("");
        etAddress.setText("");
        etContact.setText("");
    }

    public void onReact() {
        firstTabSubmitFragment = new FirstTabSubmitFragment();
        android.app.FragmentTransaction fragmentTransaction = mFirstFragmentManager.beginTransaction();
        fragmentTransaction.replace(
                flTab1Frame.getId(), firstTabSubmitFragment)
                .addToBackStack("tab1")
                .commit();
    }

    public void onViewReact() {
        thirdTabViewReactFragment = new ThirdTabViewReactFragment();
        android.app.FragmentTransaction fragmentTransaction = mThirdFragmentManager.beginTransaction();
        fragmentTransaction.replace(
                flTab3Frame.getId(), thirdTabViewReactFragment)
                .addToBackStack("tab3")
                .commit();
    }

    public void onDeletePost() {
        DatabaseReference ref = database.getReference().child("posts").child(postID);
        ref.setValue(null);
    }

    public void onListView() {
        android.app.FragmentTransaction fragmentTransaction = mFirstFragmentManager.beginTransaction();
        fragmentTransaction.replace(
                flTab1Frame.getId(), firstTabListFragment)
                .addToBackStack("tab1")
                .commit();
    }

    public void onSubmitReact(String description, String address, String contact) {
        if (description.isEmpty()) {
            Toast.makeText(this, "Please input Description.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (address.isEmpty()) {
            Toast.makeText(this, "Please input Address.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (contact.isEmpty()) {
            Toast.makeText(this, "Please input Contact.", Toast.LENGTH_SHORT).show();
            return;
        }
        ReactModel react = new ReactModel(authID, MyApplication.username, description, address, contact);
        DatabaseReference ref = database.getReference().child("reacts");
        ref.push().setValue(react);
        onListView();
    }
}
