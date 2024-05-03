package com.example.quizdean;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Quiz extends AppCompatActivity {

    private TextView question, a, b, c, d;
    private Button next;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference().child("Questions");

    String quizQuestion;
    String quizAnswerA;
    String quizAnswerB;
    String quizAnswerC;
    String quizAnswerD;
    String quizCorrectAnswer;
    int questionCount;
    int questionNumber = 1;
static TextView txtCorrect;
static TextView txtWrong;
    String userAnswer;
    int userCorrect;
    Button btnFinish;
    int correctAnswer = 0;
    int wrongAnswer = 0;
    View fragmentContainerView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        btnFinish = findViewById(R.id.btnFinish);
        fragmentContainerView = findViewById(R.id.fragmentContainerView);
        fragmentContainerView.setVisibility(View.GONE);


        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Quiz.this,ResultActivity.class);
//                startActivity(intent);
                fragmentContainerView.setVisibility(View.VISIBLE);
                fragmentContainerView.setZ(10f);
                ResultFragment resultFragment = new ResultFragment();
                Bundle bundle = new Bundle();
                bundle.putString("correct", Quiz.totalCorrectAnswer());
                bundle.putString("wrong", Quiz.totalWrongAnswer());
                resultFragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, resultFragment).commit();


            }
        });



        question = findViewById(R.id.txtQuestion);
        a = findViewById(R.id.btnA);
        b = findViewById(R.id.btnB);
        c = findViewById(R.id.btnC);
        d = findViewById(R.id.btnD);
        next = findViewById(R.id.btnNext);
        txtCorrect = findViewById(R.id.numCorrect);
        txtWrong = findViewById(R.id.numWrong);

        quiz();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a.setClickable(true);
                b.setClickable(true);
                c.setClickable(true);
                d.setClickable(true);
                quiz();
            }
        });

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAnswer = "a";
                if (quizCorrectAnswer.equals(userAnswer)){
                    a.setBackgroundColor(Color.GREEN);
                    b.setClickable(false);
                    c.setClickable(false);
                    d.setClickable(false);
                    correctAnswer = correctAnswer+1;
                    txtCorrect.setText(""+ correctAnswer);


                }
                else {
                    a.setBackgroundColor(Color.RED);
                    b.setClickable(false);
                    c.setClickable(false);
                    d.setClickable(false);
                    wrongAnswer = wrongAnswer + 1;
                    txtWrong.setText(""+ wrongAnswer);
                    findAnswer();
                }
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAnswer = "b";
                if (quizCorrectAnswer.equals(userAnswer)){
                    b.setBackgroundColor(Color.GREEN);
                    a.setClickable(false);
                    c.setClickable(false);
                    d.setClickable(false);
                    correctAnswer = correctAnswer+1;
                    txtCorrect.setText(""+ correctAnswer);

                }
                else {
                    b.setBackgroundColor(Color.RED);
                    a.setClickable(false);
                    c.setClickable(false);
                    d.setClickable(false);
                    wrongAnswer = wrongAnswer + 1;
                    txtWrong.setText(""+ wrongAnswer);
                    findAnswer();
                }

            }
        });

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAnswer = "c";
                if (quizCorrectAnswer.equals(userAnswer)){
                    c.setBackgroundColor(Color.GREEN);
                    b.setClickable(false);
                    a.setClickable(false);
                    d.setClickable(false);

                    correctAnswer = correctAnswer+1;
                    txtCorrect.setText(""+ correctAnswer);

                }
                else {
                    c.setBackgroundColor(Color.RED);
                    b.setClickable(false);
                    a.setClickable(false);
                    d.setClickable(false);
                    wrongAnswer = wrongAnswer + 1;
                    txtWrong.setText(""+ wrongAnswer);
                    findAnswer();
                }

            }
        });

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAnswer = "d";
                if (quizCorrectAnswer.equals(userAnswer)){
                    d.setBackgroundColor(Color.GREEN);
                    b.setClickable(false);
                    c.setClickable(false);
                    a.setClickable(false);
                    correctAnswer = correctAnswer+1;
                    txtCorrect.setText(""+ correctAnswer);



                }
                else {
                    d.setBackgroundColor(Color.RED);
                    b.setClickable(false);
                    c.setClickable(false);
                    a.setClickable(false);
                    wrongAnswer = wrongAnswer + 1;
                    txtWrong.setText(""+ wrongAnswer);
                    findAnswer();
                }

            }
        });
    }
    public static String totalCorrectAnswer(){
        return txtCorrect.getText().toString();
    }
    public static String totalWrongAnswer(){
        return txtWrong.getText().toString();
    }
    public void findAnswer(){
        if (quizCorrectAnswer.equals("a")){
            a.setBackgroundColor(Color.GREEN);
        } else if (quizCorrectAnswer.equals("b")) {
            b.setBackgroundColor(Color.GREEN);
        } else if (quizCorrectAnswer.equals("c")) {
            c.setBackgroundColor(Color.GREEN);
        } else if (quizCorrectAnswer.equals("d")){
            d.setBackgroundColor(Color.GREEN);
        }
    }

    public void quiz(){
        a.setBackgroundColor(Color.parseColor("#808080"));
        b.setBackgroundColor(Color.parseColor("#808080"));
        c.setBackgroundColor(Color.parseColor("#808080"));
        d.setBackgroundColor(Color.parseColor("#808080"));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                questionCount = (int) dataSnapshot.getChildrenCount();

                quizQuestion = dataSnapshot.child(String.valueOf(questionNumber)).child("q").getValue().toString();
                quizAnswerA = dataSnapshot.child(String.valueOf(questionNumber)).child("a").getValue().toString();
                quizAnswerB = dataSnapshot.child(String.valueOf(questionNumber)).child("b").getValue().toString();
                quizAnswerC = dataSnapshot.child(String.valueOf(questionNumber)).child("c").getValue().toString();
                quizAnswerD = dataSnapshot.child(String.valueOf(questionNumber)).child("d").getValue().toString();
                quizCorrectAnswer = dataSnapshot.child(String.valueOf(questionNumber)).child("answer").getValue().toString();

                question.setText(quizQuestion);
                a.setText(quizAnswerA);
                b.setText(quizAnswerB);
                c.setText(quizAnswerC);
                d.setText(quizAnswerD);

                if (questionNumber<questionCount){
                    questionNumber++;
                } else {
                    btnFinish.setVisibility(View.GONE);
                    next.setText("Xem đáp án");
                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fragmentContainerView.setVisibility(View.VISIBLE);
                            fragmentContainerView.setZ(10f);
                            ResultFragment resultFragment = new ResultFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("correct", Quiz.totalCorrectAnswer());
                            bundle.putString("wrong", Quiz.totalWrongAnswer());
                            resultFragment.setArguments(bundle);

                            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, resultFragment).commit();
                        }
                    });
                    Toast.makeText(Quiz.this, "You answered all questions", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(Quiz.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

