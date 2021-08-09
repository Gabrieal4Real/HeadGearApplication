package com.example.headgear.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.headgear.Interface.ItemListener;
import com.example.headgear.R;

public class ForumCommentAdapter extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public TextView mUserName;
    public TextView mCommentMessage;
    public ItemListener itemListener;

    public ForumCommentAdapter(@NonNull View itemView) {
        super(itemView);
        mUserName = itemView.findViewById(R.id.commentUserIDTextView);
        mCommentMessage = itemView.findViewById(R.id.commentTextView);

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
