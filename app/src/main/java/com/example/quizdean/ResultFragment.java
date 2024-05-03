package com.example.quizdean;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ResultFragment extends Fragment {
    TextView txtCorrect, txtWrong;
    Button btnExit;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        txtCorrect = view.findViewById(R.id.txtCorrect);
        txtWrong = view.findViewById(R.id.txtWrong);
        btnExit = view.findViewById(R.id.btnExit);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            txtCorrect.setText(bundle.getString("correct"));
            txtWrong.setText(bundle.getString("wrong"));
        }

        return view;
    }
}