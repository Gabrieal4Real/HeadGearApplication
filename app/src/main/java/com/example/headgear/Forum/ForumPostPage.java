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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.headgear.Adapter.ForumPostAdapter;
import com.example.headgear.Home.Homepage;
import com.example.headgear.Interface.ItemListener;
import com.example.headgear.Model.ForumPost;
import com.example.headgear.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ForumPostPage extends AppCompatActivity {

    private static String ACTIVITY_TAG = ForumPostPage.class.getSimpleName();
    private static String HEADGEAR_PREFS = "APP_PREFS";
    private Context context = ForumPostPage.this;
    private FirebaseRecyclerAdapter<ForumPost, ForumPostAdapter> recyclerAdapter;
    private RecyclerView recyclerView;
    private EditText postedit;
    private SharedPreferences sharedPreferences;
    private Button post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_page);
        sharedPreferences = getSharedPreferences(HEADGEAR_PREFS, MODE_PRIVATE);
        MeowBottomNavigation bottomNavigation4 = findViewById(R.id.meowBottomNavigationforum);
        bottomNavigation4.add(new MeowBottomNavigation.Model(1, R.drawable.ic_homepage));

        bottomNavigation4.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
            }
        });

        bottomNavigation4.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                if (item.getId() == 1) {
                    onBackPressed();
                }
            }
        });

        bottomNavigation4.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
            }
        });

        recyclerView = findViewById(R.id.recyclerView2);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 1,
                RecyclerView.VERTICAL, true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        postedit = findViewById(R.id.edittextPost);
        post = findViewById(R.id.Post);

        post.setOnClickListener(postListener);
        getAllPostFromDatabase();
    }

    private View.OnClickListener postListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (postedit.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "You can't Post A Blank Story",
                        Toast.LENGTH_SHORT).show();
            } else {
                DatabaseReference dDatabase =
                        FirebaseDatabase.getInstance().getReference("ForumPost");

                ForumPost forumPost = new ForumPost(postedit.getText().toString(),
                        sharedPreferences.getString("User ID", ""));
                dDatabase.push().setValue(forumPost)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "Posted Successfully!",
                                        Toast.LENGTH_SHORT)
                                        .show();
                                postedit.setText("");
                            }
                        });
            }
        }

    };
    private void getAllPostFromDatabase() {
        Log.d(ACTIVITY_TAG, "Getting all post from database");
        final DatabaseReference databaseReference =
                FirebaseDatabase.getInstance().getReference();
        final Query GET_ALL_POST = databaseReference.child("ForumPost");

        FirebaseRecyclerOptions<ForumPost> options =
                new FirebaseRecyclerOptions.Builder<ForumPost>()
                        .setQuery(GET_ALL_POST, ForumPost.class).build();

        recyclerAdapter = new FirebaseRecyclerAdapter<ForumPost, ForumPostAdapter>(options) {

            @Override
            protected void onBindViewHolder(@NonNull final ForumPostAdapter holder,
                                            int position, @NonNull ForumPost model) {

                holder.mUserName.setText(model.getUserID());
                holder.mPostMessage.setText(model.getMessage());

                holder.setItemListener(new ItemListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Log.d(ACTIVITY_TAG, "Cart is Clicked");

                        Intent intent = new Intent(ForumPostPage.this, ForumCommentPage.class);
                        intent.putExtra("Key", getRef(position).getKey());
                        startActivity(intent);
                        overridePendingTransition(R.anim.sliderighttoleft, R.anim.slidlefttoright);
                        finish();
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        //if else statement for only USERID
                        Log.d(ACTIVITY_TAG, "Cart is Long Clicked");
                        if (sharedPreferences.getString("User ID", "").
                                equals(holder.mUserName.getText())) {
                            getSnapshots().getSnapshot(position).getRef().removeValue();
                        } else
                            Toast.makeText(getApplicationContext(),
                                    "You Can't Delete Another Person's Story",
                                    Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @NonNull
            @Override
            public ForumPostAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(context)
                        .inflate(R.layout.forum_post_row, null, false);
                return new ForumPostAdapter(view);
            }

        };
        recyclerView.setAdapter(recyclerAdapter);

    }

    @Override
    public void onBackPressed() { //Copy to all folders
        super.onBackPressed();
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slidlefttoright, R.anim.sliderighttoleft);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        recyclerAdapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerAdapter.startListening();
    }
}
