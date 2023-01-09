package com.example.testowanieinterfejsu60;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class iconQuiz extends AppCompatActivity
{
    TextView questionText;
    Button answerA,answerB,answerC;
    Button cancelTest, sendValue;
    ImageView currentIcon;
    int score=0;
    int currentQuestion=0;
    int numberOfQuestions=(iconQuestion.answersCorrect.length);
    int points[]= new int[numberOfQuestions];
    String chosenAnswer="";
    boolean isSomethingChosen=false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_quiz);

        questionText = findViewById(R.id.question);
        questionText.setText(iconQuestion.question);

        currentIcon=findViewById(R.id.quiz_icon);

        answerA = findViewById(R.id.answer_A);
        answerA.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                answerB.setBackgroundColor(Color.parseColor("#6200ee"));
                answerC.setBackgroundColor(Color.parseColor("#6200ee"));
                chosenAnswer=answerA.getText().toString();
                isSomethingChosen=true;
                answerA.setBackgroundColor(Color.parseColor("#ee008b"));
            }
        });

        answerB = findViewById(R.id.answer_B);
        answerB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                answerA.setBackgroundColor(Color.parseColor("#6200ee"));
                answerC.setBackgroundColor(Color.parseColor("#6200ee"));
                chosenAnswer=answerB.getText().toString();
                isSomethingChosen=true;
                answerB.setBackgroundColor(Color.parseColor("#ee008b"));
            }
        });


        answerC = findViewById(R.id.answer_C);
        answerC.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                answerA.setBackgroundColor(Color.parseColor("#6200ee"));
                answerB.setBackgroundColor(Color.parseColor("#6200ee"));
                chosenAnswer=answerC.getText().toString();
                isSomethingChosen=true;
                answerC.setBackgroundColor(Color.parseColor("#ee008b"));
            }
        });

        loadNextQuestion();

        cancelTest=findViewById(R.id.cancel_icon_quiz);
        cancelTest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                cancelCurrentTest();
            }
        });

        sendValue=findViewById(R.id.send_icon_quiz);
        sendValue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               checkIfChosen();
            }
        });
    }

    void loadNextQuestion()
    {
        if (currentQuestion==numberOfQuestions)
        {
            finishQuiz();
            return;
        }
        isSomethingChosen=false;

        answerA.setBackgroundColor(Color.parseColor("#6200ee"));
        answerB.setBackgroundColor(Color.parseColor("#6200ee"));
        answerC.setBackgroundColor(Color.parseColor("#6200ee"));


        answerA.setText(iconQuestion.answers[currentQuestion][0]);
        answerB.setText(iconQuestion.answers[currentQuestion][1]);
        answerC.setText(iconQuestion.answers[currentQuestion][2]);
        currentIcon.setImageResource(iconQuestion.image[currentQuestion]);
    }

    void finishQuiz()
    {
        appDatabase myDB = new appDatabase(iconQuiz.this);
        myDB.addIconQuiz(score, points);
        String finalScore="Twój wynik to " + score;
        new AlertDialog.Builder(this)
                .setTitle("Koniec quizu")
                .setMessage(finalScore)
                .setPositiveButton("Zakończ",((dialogInterface, i) -> backToMain()))
                .setCancelable(false)
                .show();
    }

    void checkIfChosen()
    {
        if(isSomethingChosen==true)
        {
            if (chosenAnswer.equals(iconQuestion.answersCorrect[currentQuestion]))
            {
                score++;
                points[currentQuestion]=1;
                isCorrect();
            }
            else
            {
                points[currentQuestion]=0;
                isNotCorrect();
            }
            currentQuestion++;
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Nie zaznaczono odpowiedzi.",Toast.LENGTH_SHORT).show();
        }
    }

    void cancelCurrentTest()
    {
        new AlertDialog.Builder(this)
                .setTitle("Anulować test?")
                .setPositiveButton("Tak",((dialogInterface, i) -> backToMain()))
                .setNegativeButton("Nie",null)
                .setCancelable(true)
                .show();
    }

    void backToMain()
    {
        finish();
    }

    void isCorrect()
    {
        new AlertDialog.Builder(this)
                .setTitle("Wybrano poprawną odpowiedź")
                .setPositiveButton("OK", ((dialogInterface, i) -> loadNextQuestion()))
                .setCancelable(false)
                .show();
    }

    void isNotCorrect()
    {
        new AlertDialog.Builder(this)
                .setTitle("Wybrano błędną odpowiedź.")
                .setMessage("Poprawna odpowiedź to '" + iconQuestion.answersCorrect[currentQuestion] + "'")
                .setPositiveButton("OK", ((dialogInterface, i) -> loadNextQuestion()))
                .setCancelable(false)
                .show();
    }
}