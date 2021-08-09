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

import com.example.headgear.Aid.Anxiety.AidAnxietySub;
import com.example.headgear.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class AidAnxietyAdapter extends RecyclerView.Adapter<AidAnxietyAdapter.Anxiety> {

    List<AidAnxietySub> aidAnxietySubList;
    private ImageView arrow;

    public AidAnxietyAdapter(List<AidAnxietySub> aidAnxietySubList) {
        this.aidAnxietySubList = aidAnxietySubList;

    }

    @NonNull
    @Override
    public Anxiety onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.aid_anxiety_row, parent, false);
        return new Anxiety(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Anxiety holder, int position) {
        AidAnxietySub aidAnxietySub = aidAnxietySubList.get(position);
        holder.titleTextView.setText(aidAnxietySub.getTitle());
        holder.urlTextView.setText(aidAnxietySub.getUrl());
        holder.definitionTextView.setText(aidAnxietySub.getDefinition());


        String url = holder.urlTextView.getText().toString();
        Picasso.get().load(url).resize(1050, 510)
                .centerCrop().into(holder.imageView);


        boolean isExpanded = aidAnxietySubList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return aidAnxietySubList.size();
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

    class Anxiety extends RecyclerView.ViewHolder {

        ConstraintLayout expandableLayout;
        TextView titleTextView, urlTextView, definitionTextView;
        ImageView imageView;

        public Anxiety(@NonNull final View itemView) {
            super(itemView);

            arrow = itemView.findViewById(R.id.arrow);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            imageView = itemView.findViewById(R.id.imageView2);
            urlTextView = itemView.findViewById(R.id.urlTextView);

            definitionTextView = itemView.findViewById(R.id.definitionTextView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);


            titleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AidAnxietySub aidAnxietySub = aidAnxietySubList.get(getAdapterPosition());
                    aidAnxietySub.setExpanded(!aidAnxietySub.isExpanded());
                    notifyItemChanged(getAdapterPosition());

                    if (aidAnxietySub.isExpanded())
                        expand();
                    if (!aidAnxietySub.isExpanded())
                        collapse();

                }
            });
        }

    }
}