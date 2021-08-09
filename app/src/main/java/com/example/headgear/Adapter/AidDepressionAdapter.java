package com.example.headgear.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.headgear.Aid.Depression.AidDepressionSub;
import com.example.headgear.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class AidDepressionAdapter extends RecyclerView.Adapter<AidDepressionAdapter.Depression> {

    List<AidDepressionSub> aidDepressionSubList;
    private ImageView arrow;

    public AidDepressionAdapter(List<AidDepressionSub> aidDepressionSubList) {
        this.aidDepressionSubList = aidDepressionSubList;

    }

    @NonNull
    @Override
    public Depression onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.aid_depression_row, parent, false);
        return new Depression(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Depression holder, int position) {
        AidDepressionSub aidDepressionSub = aidDepressionSubList.get(position);
        holder.titleTextView.setText(aidDepressionSub.getTitle());
        holder.urlTextView.setText(aidDepressionSub.getUrl());
        holder.definitionTextView.setText(aidDepressionSub.getDefinition());

        String url = holder.urlTextView.getText().toString();
        Picasso.get().load(url).resize(1050, 510)
                .centerCrop().into(holder.imageView);

        boolean isExpanded = aidDepressionSubList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return aidDepressionSubList.size();
    }

    public void expand() {
        RotateAnimation rotate =
                new RotateAnimation(0, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(500);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }

    public void collapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 0, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(500);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }

    class Depression extends RecyclerView.ViewHolder {

        ConstraintLayout expandableLayout;
        TextView titleTextView, urlTextView, definitionTextView;
        ImageView imageView;

        public Depression(@NonNull final View itemView) {
            super(itemView);

            arrow = itemView.findViewById(R.id.arrow);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            urlTextView = itemView.findViewById(R.id.urlTextView);
            imageView = itemView.findViewById(R.id.imageView2);
            definitionTextView = itemView.findViewById(R.id.definitionTextView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);


            titleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AidDepressionSub aidDepressionSub = aidDepressionSubList.get(getAdapterPosition());
                    aidDepressionSub.setExpanded(!aidDepressionSub.isExpanded());
                    notifyItemChanged(getAdapterPosition());

                    if (aidDepressionSub.isExpanded())
                        expand();
                    if (!aidDepressionSub.isExpanded())
                        collapse();

                }
            });
        }

    }
}