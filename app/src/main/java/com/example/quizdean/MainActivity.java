package com.example.quizdean;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private TextView textView;
    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<String>titleList = new ArrayList<>();
    private ArrayList<String>dayList = new ArrayList<>();
    private ArrayList<Integer>imageList = new ArrayList<>();
    private ImageView imageView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        textView = findViewById(R.id.textView7);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        titleList.add("Fun Quiz");
        titleList.add("Câu hỏi về Captain America");
        titleList.add("Câu hỏi về Cây xanh");

        dayList.add("Vừa xong");
        dayList.add("2 ngày trước");
        dayList.add("4 ngày trước");

        imageList.add(R.drawable.congrat);
        imageList.add(R.drawable.captain);
        imageList.add(R.drawable.tree);

        adapter = new RecyclerAdapter(titleList, dayList, imageList, MainActivity.this);
        recyclerView.setAdapter(adapter);

    }

}
