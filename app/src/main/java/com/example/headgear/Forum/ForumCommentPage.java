package com.example.headgear.Forum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.headgear.Adapter.ForumCommentAdapter;
import com.example.headgear.Interface.ItemListener;
import com.example.headgear.Model.ForumComment;
import com.example.headgear.Model.ForumPost;
import com.example.headgear.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ForumCommentPage extends AppCompatActivity {

    private static String ACTIVITY_TAG = ForumCommentPage.class.getSimpleName();
    private static String HEADGEAR_PREFS = "APP_PREFS";
    private Context context = ForumCommentPage.this;
    private SharedPreferences sharedPreferences;

    private FirebaseRecyclerAdapter<ForumComment, ForumCommentAdapter> recyclerAdapter;
    private RecyclerView recyclerView;
    private EditText commentedit;
    private TextView postTextview, userIDtextView;
    private Button comment;

    //Secret Credentials
    private String Key;
    private View.OnClickListener commentListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(ACTIVITY_TAG, "Comment is now posted online");
            if (commentedit.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "You can't Post A Blank Comment",
                        Toast.LENGTH_SHORT).show();
            } else {
                DatabaseReference dDatabase = FirebaseDatabase.getInstance().getReference();
                ForumComment comment = new ForumComment(
                        commentedit.getText().toString(),
                        Key,
                        sharedPreferences.getString("User ID", "")
                );
                dDatabase.child("ForumComment").push().setValue(comment)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "Commented Successfully!",
                                        Toast.LENGTH_SHORT)
                                        .show();
                                commentedit.setText("");
                            }
                        });
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_comment_page);
        sharedPreferences = getSharedPreferences(HEADGEAR_PREFS, MODE_PRIVATE);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 1);
        postTextview = findViewById(R.id.postTextView);
        userIDtextView = findViewById(R.id.UserIDTextView);
        commentedit = findViewById(R.id.edittextComment);
        recyclerView = findViewById(R.id.recyclerView3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        comment = findViewById(R.id.Comment);

        comment.setOnClickListener(commentListener);

        if (!getIntent().getStringExtra("Key").isEmpty()) {
            Key = getIntent().getStringExtra("Key");
            getTheParticularPostOnline(Key);
            getCommentOnline(Key);
        }
    }

    private void getTheParticularPostOnline(String key) {
        Log.d(ACTIVITY_TAG, "Getting the selected post online");
        DatabaseReference dDatabase = FirebaseDatabase.getInstance().getReference();
        Query GET_THE_PARTICULAR_POST = dDatabase.child("ForumPost").child(key);

        GET_THE_PARTICULAR_POST.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ForumPost post = dataSnapshot.getValue(ForumPost.class);
                    postTextview.setText(post.getMessage());
                    userIDtextView.setText("By: " + post.getUserID());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getCommentOnline(String key) {
        Log.d(ACTIVITY_TAG, "Getting comments online");
        DatabaseReference dDatabase = FirebaseDatabase.getInstance().getReference();
        Query GET_ME_ALL_THE_COMMENTS = dDatabase.child("ForumComment").orderByChild("postID")
                .equalTo(key);

        FirebaseRecyclerOptions<ForumComment> customOptions =
                new FirebaseRecyclerOptions.Builder<ForumComment>()
                        .setQuery(GET_ME_ALL_THE_COMMENTS, ForumComment.class).build();

        recyclerAdapter = new FirebaseRecyclerAdapter<ForumComment, ForumCommentAdapter>(customOptions) {
            @Override
            protected void onBindViewHolder(@NonNull final ForumCommentAdapter holder, int position,
                                            @NonNull ForumComment model) {

                holder.mCommentMessage.setText(model.getCommentMessage());
                holder.mUserName.setText(model.getUserID());

                holder.setItemListener(new ItemListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        Log.d(ACTIVITY_TAG, "Cart is Long Clicked");
                        if (sharedPreferences.getString("User ID", "").
                                equals(holder.mUserName.getText())) {
                            getSnapshots().getSnapshot(position).getRef().removeValue();
                        } else
                            Toast.makeText(getApplicationContext(),
                                    "You Can't Delete Another Person's Comment",
                                    Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @NonNull
            @Override
            public ForumCommentAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(context).inflate(R.layout.forum_comment_row
                        , null, false);
                return new ForumCommentAdapter(view);
            }
        };

        recyclerView.setAdapter(recyclerAdapter);
    }

    public void onBackPressed() { //Copy to all folders
        super.onBackPressed();
        Intent intent = new Intent(this, ForumPostPage.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slidlefttoright, R.anim.sliderighttoleft);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(ACTIVITY_TAG, "Activity has started");
        recyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(ACTIVITY_TAG, "Activity has stopped");
        recyclerAdapter.stopListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(ACTIVITY_TAG, "Activity has destroyed");
        recyclerAdapter.stopListening();
    }
}
