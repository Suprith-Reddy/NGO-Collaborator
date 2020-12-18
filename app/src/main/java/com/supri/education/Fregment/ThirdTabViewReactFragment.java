package com.supri.education.Fregment;


import android.content.Context;
import android.os.Bundle;
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
import com.supri.education.Model.ReactModel;
import com.supri.education.MyApplication;
import com.supri.education.R;

import java.util.ArrayList;
import java.util.List;

public class ThirdTabViewReactFragment extends android.app.Fragment {


    private FirstActivity parent;
    private RecyclerView mReactList;
    private ListViewAdapter mAdapter;
    private List<ReactModel> mReacts = new ArrayList<>();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private LinearLayoutManager mLinearManager;
    private Button btnShowReact_2;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parent = (FirstActivity)getActivity();
        View view = inflater.inflate(R.layout.fregment_thirdtab_react, container, false);
        TextView tvReactDescription = (TextView)view.findViewById(R.id.tvReactDescription);
        tvReactDescription.setText(parent.postDescription);
        btnShowReact_2 = (Button)view.findViewById(R.id.btnShowReact_2);
        mReactList = view.findViewById(R.id.lvThirdTabReactList);
        getAllReacts();
        return view;
    }

    private void getAllReacts() {
        DatabaseReference ref = database.getReference().child("reacts");
        Query query = ref.orderByChild("authID").equalTo(parent.postID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mReacts.clear();
                for (DataSnapshot val:dataSnapshot.getChildren()) {
                    ReactModel react = new ReactModel(val.child("authID").getValue().toString(), val.child("username").getValue().toString(),
                            val.child("description").getValue().toString(), val.child("address").getValue().toString(), val.child("contact").getValue().toString());
                    mReacts.add(react);
                }
                btnShowReact_2.setText(Integer.toString(mReacts.size()) + " people reacted");
                mLinearManager = new LinearLayoutManager(parent);
                mReactList.setLayoutManager(mLinearManager);
                mAdapter = new ListViewAdapter(parent, mReacts);
                mReactList.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {
        Context context;
        List<ReactModel> mReacts;
        public ListViewAdapter(Context con, List<ReactModel> reacts) {
            context = con;
            mReacts = reacts;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_thirdtab_react, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final ReactModel react = mReacts.get(position);
            holder.tvReactDetail.setText(Integer.toString(position + 1) + " .  " + react.getUsername() + "\n      Description : " + react.getDescription() + "\n      Address : "
            + react.getAddress() + "\n      Contact : " + react.getContact());
        }

        @Override
        public int getItemCount() {
            return mReacts.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvReactDetail;
            public ViewHolder(View view) {
                super(view);
                tvReactDetail = (TextView)view.findViewById(R.id.tvReactDetail);
            }
        }
    }
}