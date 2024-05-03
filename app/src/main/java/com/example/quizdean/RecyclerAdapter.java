package com.example.quizdean;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.QuizViewHolder>{
    private ArrayList<String> titleList;
    private ArrayList<String>dayList;
    private ArrayList<Integer>imageList;
    private Context context;

    public RecyclerAdapter(ArrayList<String> titleList, ArrayList<String> dayList, ArrayList<Integer> imageList, Context context) {
        this.titleList = titleList;
        this.dayList = dayList;
        this.imageList = imageList;
        this.context = context;
    }

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder holder, int position) {



        holder.textViewTitle.setText(titleList.get(position));
        holder.textViewDay.setText(dayList.get(position));
        holder.imageView.setImageResource(imageList.get(position));



        holder.cardView.setOnClickListener(v -> {
            if (position == 0){
                Toast.makeText(context,"1",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,Quiz.class);
                context.startActivity(intent);

            } else if (position == 1){
                Toast.makeText(context,"2",Toast.LENGTH_SHORT).show();


            } else if (position == 2){
                Toast.makeText(context,"3",Toast.LENGTH_SHORT).show();

            }
        });


    }




    @Override
    public int getItemCount() {
        return titleList.size();
    }

    public class QuizViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle, textViewDay;
        private ImageView imageView;
        private CardView cardView;
        public QuizViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.txtTitle);
            textViewDay = itemView.findViewById(R.id.txtDay);
            imageView = itemView.findViewById(R.id.imgDang);
            cardView = itemView.findViewById(R.id.cardView);


        }
    }




}

