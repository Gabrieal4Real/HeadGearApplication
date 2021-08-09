package com.example.headgear.Interface;

import android.view.View;

public interface ItemListener {
    void onClick(View view, int position, boolean isLongClick);

    void onLongClick(View view, int position);
}