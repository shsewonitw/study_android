package com.tje.practice_membermanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MemberRecyclerViewAdapter extends RecyclerView.Adapter<MemberRecyclerViewAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView age;
        public TextView phone;
        public TextView address;
        public TextView registDate;

        public ViewHolder(@NonNull View view) {
            super(view);

            name = view.findViewById(R.id.tv_recycle_name);
            age = view.findViewById(R.id.tv_recycle_age);
            phone = view.findViewById(R.id.tv_recycle_phone);
            address = view.findViewById(R.id.tv_recycle_address);
            registDate = view.findViewById(R.id.tv_recycle_registDate);
        }
    }

    @NonNull
    @Override
    public MemberRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_member,parent,false);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member,parent,false);
        ViewHolder vh =new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MemberRecyclerViewAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
