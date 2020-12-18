package com.supri.education.Fregment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.supri.education.FirstActivity;
import com.supri.education.Model.PostModel;
import com.supri.education.MyApplication;
import com.supri.education.R;

import java.util.ArrayList;
import java.util.List;

public class ThirdTabMyPostFragment extends android.app.Fragment {


    private FirstActivity parent;
    private RecyclerView lvThirdTabList;
    private List<PostModel> mPostList = new ArrayList<>();
    private ListViewAdapter mAdapter;
    private LinearLayoutManager mLinearManager;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parent = (FirstActivity)getActivity();
        View view = inflater.inflate(R.layout.fregment_thirdtab_post, container, false);
        lvThirdTabList = (RecyclerView)view.findViewById(R.id.lvThirdTabList);
        mLinearManager = new LinearLayoutManager(parent);
        getMyPost();
        return view;
    }

    public void getMyPost() {
        DatabaseReference ref = database.getReference().child("posts");
        Query query = ref.orderByChild("username").equalTo(MyApplication.username);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mPostList.clear();
                for (DataSnapshot val:dataSnapshot.getChildren()) {
                    PostModel post = new PostModel(val.child("username").getValue().toString(), val.child("description").getValue().toString(),
                            val.child("address").getValue().toString(), val.child("contact").getValue().toString());
                    post.setTimestamp(val.child("timestamp").getValue().toString());
                    post.setId(val.getRef().getKey());
                    mPostList.add(post);
                }
                lvThirdTabList.setLayoutManager(mLinearManager);
                mAdapter = new ListViewAdapter(parent, mPostList);
                lvThirdTabList.setAdapter(mAdapter);
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
                    .inflate(R.layout.list_item_thirdtab, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final PostModel post = mPostList.get(position);
            holder.tvMyDescription.setText(post.getDescription());
            holder.postID = post.getId();
            DatabaseReference ref = database.getReference().child("reacts");
            Query query = ref.orderByChild("authID").equalTo(post.getId());
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int count = 0;
                    for (DataSnapshot val:dataSnapshot.getChildren()) {
                            count++;
                    }
                    holder.btnShowReact.setText(Integer.toString(count) + " people reacted");
                    holder.reactedCount = count;
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
            public TextView tvMyDescription;
            public Button btnShowReact;
            public Button btnDelete;
            public int reactedCount;
            public String postID;
            public ViewHolder(View view) {
                super(view);
                tvMyDescription = (TextView)view.findViewById(R.id.tvMyDescription);
                reactedCount = 0;
                btnShowReact = (Button)view.findViewById(R.id.btnShowReact);
                btnShowReact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (reactedCount == 0) return;
                        parent.postID = postID;
                        parent.postDescription = tvMyDescription.getText().toString();
                        parent.onViewReact();
                    }
                });
                btnDelete = (Button)view.findViewById(R.id.btnDelete);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        parent.postID = postID;
                        parent.onDeletePost();
                    }
                });
            }
        }
    }
}