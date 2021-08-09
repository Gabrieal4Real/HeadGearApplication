package com.example.headgear.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.headgear.Interface.ItemListener;
import com.example.headgear.R;

public class ForumPostAdapter extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public TextView mUserName;
    public TextView mPostMessage;
    public ItemListener itemListener;

    public ForumPostAdapter(@NonNull View itemView) {
        super(itemView);
        mUserName = itemView.findViewById(R.id.postUserIDTextView);
        mPostMessage = itemView.findViewById(R.id.PostTextView);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    @Override
    public void onClick(View v) {
        itemListener.onClick(v, getAdapterPosition(), false);
    }


    @Override
    public boolean onLongClick(View v) {
        itemListener.onLongClick(v, getAdapterPosition());
        return true;
    }
}
