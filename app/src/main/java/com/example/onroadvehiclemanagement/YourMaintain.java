package com.example.onroadvehiclemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Context;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Adapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class YourMaintain extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
//    private List<Contact> contacts;
    private Adapter adapter;
    private ApiInterface apiInterface;
    ProgressBar progressBar;
    TextView search;
    String[] item;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_maintain);

        key = getIntent().getStringExtra("EXTRA_USERNAME_HOME");
        progressBar = findViewById(R.id.prograss);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //
    }

    public void fetchContact(String type, String key){

//        apiInterface = Ap
    }
}