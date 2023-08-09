package com.iiro.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FriendsActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener{
    private RecyclerView recycler;
    private ArrayList<User> users;
    private ProgressBar progressBar;
    private UsersAdapter usersAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    UsersAdapter.OnUserClickListener onUserClickListener;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);

        recycler = findViewById(R.id.recycler);
        users = new ArrayList<>();
        progressBar = findViewById(R.id.progressBar);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUsers();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        onUserClickListener = new UsersAdapter.OnUserClickListener() {
            @Override
            public void onUserClicked(int position) {
                Toast.makeText(FriendsActivity.this, "Tapped on " + users.get(position).getUsername(), Toast.LENGTH_SHORT).show();
            }
        };

        getUsers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_profile) {
            startActivity(new Intent(FriendsActivity.this, ProfileActivity.class));
            return true;
        }
        return false;
    }

    private void getUsers(){
        users.clear();
        FirebaseDatabase.getInstance().getReference("user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    users.add(dataSnapshot.getValue(User.class));
                }
                usersAdapter = new UsersAdapter(users, FriendsActivity.this, onUserClickListener);
                recycler.setLayoutManager(new LinearLayoutManager(FriendsActivity.this));
                recycler.setAdapter(usersAdapter);
                progressBar.setVisibility(View.GONE);
                recycler.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}