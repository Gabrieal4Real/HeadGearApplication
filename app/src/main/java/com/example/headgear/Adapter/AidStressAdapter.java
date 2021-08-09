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

import com.example.headgear.Aid.Stress.AidStressSub;
import com.example.headgear.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class AidStressAdapter extends RecyclerView.Adapter<AidStressAdapter.Stress> {

    List<AidStressSub> aidStressSubList;
    private ImageView arrow;

    public AidStressAdapter(List<AidStressSub> aidStressSubList) {
        this.aidStressSubList = aidStressSubList;

    }

    @NonNull
    @Override
    public Stress onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.aid_stress_row, parent, false);
        return new Stress(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Stress holder, int position) {

        AidStressSub aidStressSub = aidStressSubList.get(position);
        holder.titleTextView.setText(aidStressSub.getTitle());
        holder.urlTextView.setText(aidStressSub.getUrl());
        holder.definitionTextView.setText(aidStressSub.getDefinition());

        String url = holder.urlTextView.getText().toString();
        Picasso.get().load(url).resize(1050, 510)
                .centerCrop().into(holder.imageView);

        boolean isExpanded = aidStressSubList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return aidStressSubList.size();
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

    class Stress extends RecyclerView.ViewHolder {


        ConstraintLayout expandableLayout;
        TextView titleTextView, urlTextView, definitionTextView;
        ImageView imageView;

        public Stress(@NonNull final View itemView) {
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

                    AidStressSub aidStressSub = aidStressSubList.get(getAdapterPosition());
                    aidStressSub.setExpanded(!aidStressSub.isExpanded());
                    notifyItemChanged(getAdapterPosition());

                    if (aidStressSub.isExpanded())
                        expand();
                    if (!aidStressSub.isExpanded())
                        collapse();

                }
            });
        }

    }
}