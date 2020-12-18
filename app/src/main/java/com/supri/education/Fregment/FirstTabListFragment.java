package com.supri.education.Fregment;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.LinearLayout;
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

public class FirstTabListFragment extends android.app.Fragment {


    private FirstActivity parent;
    private RecyclerView lvFirstTabList;
    private List<PostModel> mPostList = new ArrayList<>();
    private ListViewAdapter mAdapter;
    private LinearLayoutManager mLinearManager;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parent = (FirstActivity)getActivity();
        View view = inflater.inflate(R.layout.fregment_firsttab_list, container, false);
        lvFirstTabList = (RecyclerView)view.findViewById(R.id.lvFirstTabList);
        mLinearManager = new LinearLayoutManager(parent);
        getAllPost();
        return view;
    }

    public void getAllPost() {
        DatabaseReference ref = database.getReference().child("posts");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mPostList.clear();
                for (DataSnapshot val:dataSnapshot.getChildren()) {
                    if (val.child("username").getValue().toString().equals(MyApplication.username)) continue;
                    PostModel post = new PostModel(val.child("username").getValue().toString(), val.child("description").getValue().toString(),
                            val.child("address").getValue().toString(), val.child("contact").getValue().toString());
                    post.setId(val.getKey());
                    post.setTimestamp(val.child("timestamp").getValue().toString());
                    mPostList.add(post);
                }
                lvFirstTabList.setLayoutManager(mLinearManager);
                mAdapter = new ListViewAdapter(parent, mPostList);
                lvFirstTabList.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {
        Context context;
        List<PostModel> mPostList;
        public ListViewAdapter(Context con, List<PostModel> posts) {
            context = con;
            mPostList = posts;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_firsttab, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        private String ChangeDescription(String description) {
            if (description.length() < 20) return description;
            return description.subSequence(0, 20) + " ...";
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            PostModel post = mPostList.get(getItemCount() - position - 1);
            holder.tvUserName.setText(post.getUsername());
            holder.tvTime.setText("Time : " + MyApplication.getFormattedDateTimeString("hh:mm:ss a dd/MM/yyyy",  Long.parseLong(post.getTimestamp())));
            holder.tvDescription.setText(ChangeDescription(post.getDescription()));
            holder.descriptionDetails = "Description : " + post.getDescription() +
                                        "\n\nContact : " + post.getContact() +
                                        "\n\nAddress : " + post.getAddress();
            holder.postID = post.getId();
            String username = post.getUsername();
            DatabaseReference ref = database.getReference().child("users");
            ref.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot val:dataSnapshot.getChildren()) {
                        if (val.child("type").getValue().equals("NGO")) {
                            holder.tvNGOType.setText("NGO");
                        } else {
                            holder.tvNGOType.setText("");
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        @Override
        public int getItemCount() {
            return mPostList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvUserName;
            public TextView tvTime;
            public TextView tvDescription;
            public TextView tvNGOType;
            public String postID;
            public String descriptionDetails;
            public LinearLayout llListViewItem;
            public ViewHolder(View view) {
                super(view);
                tvUserName = (TextView)view.findViewById(R.id.tvUserName);
                tvTime = (TextView)view.findViewById(R.id.tvTime);
                tvDescription = (TextView)view.findViewById(R.id.tvDescription);
                llListViewItem = (LinearLayout)view.findViewById(R.id.llListViewItem);
                tvNGOType = (TextView)view.findViewById(R.id.tvNGOType);

                Button btnReact = (Button)view.findViewById(R.id.btnReact);
                btnReact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        parent.authID = postID;
                        parent.onReact();
                    }
                });
                llListViewItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog alertDialog = new AlertDialog.Builder(parent).create();
                        alertDialog.setTitle("Details");
                        alertDialog.setMessage(descriptionDetails);
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                });
            }
        }
    }
}