package com.example.testowanieinterfejsu60;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class colorPick extends AppCompatActivity
{
    Button cancelTest, sendValue;

    LinearLayout optionA, optionB;
    TextView titleA, titleB, textA, textB;
    Button buttonA, buttonB;

    int numberOfPairs=(colorPickClass.colorPairsMain.length);
    boolean isSomethingChosen=false;
    int AorB;
    int startingColors[][]=new int[numberOfPairs/2][2];
    int chosenColors[]=new int[numberOfPairs/2];
    int semiFinalColors[][]=new int[numberOfPairs/4][2];
    int finalColors[]=new int[2];
    int winnerColor;
    int stageOnePair=0;
    int stageTwoPair=0;
    boolean isStageOneOver=false;
    boolean isSecondStageOver=false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_pick);

        titleA=findViewById(R.id.title_option_A);
        titleB=findViewById(R.id.title_option_B);
        textA=findViewById(R.id.text_option_A);
        textB=findViewById(R.id.text_option_B);
        buttonA=findViewById(R.id.button_option_A);
        buttonB=findViewById(R.id.button_option_B);
        buttonA.setClickable(false);
        buttonB.setClickable(false);

        optionA = findViewById(R.id.color_option_A);
        optionA.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                optionB.setBackgroundColor(Color.WHITE);
                isSomethingChosen=true;
                AorB=1;
                optionA.setBackgroundColor(Color.parseColor("#32CD32"));
            }
        });

        optionB = findViewById(R.id.color_option_B);
        optionB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                optionA.setBackgroundColor(Color.WHITE);
                isSomethingChosen=true;
                AorB=2;
                optionB.setBackgroundColor(Color.parseColor("#32CD32"));
            }
        });

        fillStartingColors();
        stageOne();

        cancelTest=findViewById(R.id.cancel_color_pick);
        cancelTest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                cancelCurrentTest();
            }
        });

        sendValue=findViewById(R.id.send_color_pick);
        sendValue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                checkIfChosen();
            }
        });
    }

    void finishPick()
    {
        appDatabase myDB = new appDatabase(colorPick.this);
        myDB.addColorPick(winnerColor);
        new AlertDialog.Builder(this)
                .setTitle("Koniec")
                .setMessage("Dane zostały przesłane do bazy.")
                .setPositiveButton("Zakończ",((dialogInterface, i) -> backToMain()))
                .setCancelable(false)
                .show();
    }

    void checkIfChosen()
    {
        if(isSomethingChosen==true && isStageOneOver==false)
        {
            fillChosenColors();
            stageOnePair++;
            stageOne();
        }
        else if(isSomethingChosen==true && isStageOneOver==true && isSecondStageOver==false)
        {
            fillFinalColors();
            stageTwoPair++;
            stageTwo();
        }
        else if (isSomethingChosen==true && isSecondStageOver==true)
        {
            declareWinner();
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

    void fillStartingColors()
    {
       int i=0;
       for (int j=0; j<numberOfPairs/2; j++)
       {
           startingColors[j][0]=i;
           i++;
           startingColors[j][1]=i;
           i++;
       }
    }

    void stageOne()
    {
        if (stageOnePair==numberOfPairs/2)
        {
            isStageOneOver=true;
            fillSemiFinalColors();
            stageTwo();
            return;
        }
        isSomethingChosen=false;

        optionA.setBackgroundColor(Color.WHITE);
        optionB.setBackgroundColor(Color.WHITE);

        titleA.setBackgroundColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsMain[startingColors[stageOnePair][0]])));
        titleB.setBackgroundColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsMain[startingColors[stageOnePair][1]])));

        titleA.setTextColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsSecondary[startingColors[stageOnePair][0]])));
        titleB.setTextColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsSecondary[startingColors[stageOnePair][1]])));

        textA.setTextColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsMain[startingColors[stageOnePair][0]])));
        textB.setTextColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsMain[startingColors[stageOnePair][1]])));

        buttonA.setBackgroundColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsMain[startingColors[stageOnePair][0]])));
        buttonB.setBackgroundColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsMain[startingColors[stageOnePair][1]])));

        buttonA.setTextColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsSecondary[startingColors[stageOnePair][0]])));
        buttonB.setTextColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsSecondary[startingColors[stageOnePair][1]])));
    }

    void fillChosenColors()
    {
        if (AorB==1)
        {
            chosenColors[stageOnePair]=startingColors[stageOnePair][0];
        }
        else if(AorB==2)
        {
            chosenColors[stageOnePair]=startingColors[stageOnePair][1];
        }
    }

    void fillSemiFinalColors()
    {
        int i=0;
        for (int j=0; j<numberOfPairs/4; j++)
        {
            semiFinalColors[j][0]=chosenColors[i];
            i++;
            semiFinalColors[j][1]=chosenColors[i];
            i++;
        }
    }

    void stageTwo()
    {
        if (stageTwoPair==numberOfPairs/4)
        {
            isSecondStageOver=true;
            finalStage();
            return;
        }
        isSomethingChosen=false;

        optionA.setBackgroundColor(Color.WHITE);
        optionB.setBackgroundColor(Color.WHITE);

        titleA.setBackgroundColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsMain[semiFinalColors[stageTwoPair][0]])));
        titleB.setBackgroundColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsMain[semiFinalColors[stageTwoPair][1]])));

        titleA.setTextColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsSecondary[semiFinalColors[stageTwoPair][0]])));
        titleB.setTextColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsSecondary[semiFinalColors[stageTwoPair][1]])));

        textA.setTextColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsMain[semiFinalColors[stageTwoPair][0]])));
        textB.setTextColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsMain[semiFinalColors[stageTwoPair][1]])));

        buttonA.setBackgroundColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsMain[semiFinalColors[stageTwoPair][0]])));
        buttonB.setBackgroundColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsMain[semiFinalColors[stageTwoPair][1]])));

        buttonA.setTextColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsSecondary[semiFinalColors[stageTwoPair][0]])));
        buttonB.setTextColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsSecondary[semiFinalColors[stageTwoPair][1]])));
    }

    void fillFinalColors()
    {
        if (AorB==1)
        {
            finalColors[stageTwoPair]=semiFinalColors[stageTwoPair][0];
        }
        else if(AorB==2)
        {
            finalColors[stageTwoPair]=semiFinalColors[stageTwoPair][1];
        }
    }

    void finalStage()
    {
        isSomethingChosen=false;

        optionA.setBackgroundColor(Color.WHITE);
        optionB.setBackgroundColor(Color.WHITE);

        titleA.setBackgroundColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsMain[finalColors[0]])));
        titleB.setBackgroundColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsMain[finalColors[1]])));

        titleA.setTextColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsSecondary[finalColors[0]])));
        titleB.setTextColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsSecondary[finalColors[1]])));

        textA.setTextColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsMain[finalColors[0]])));
        textB.setTextColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsMain[finalColors[1]])));

        buttonA.setBackgroundColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsMain[finalColors[0]])));
        buttonB.setBackgroundColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsMain[finalColors[1]])));

        buttonA.setTextColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsSecondary[finalColors[0]])));
        buttonB.setTextColor(Color.parseColor(String.valueOf(colorPickClass.colorPairsSecondary[finalColors[1]])));
    }

    void declareWinner()
    {
        if (AorB==1)
        {
            winnerColor=finalColors[0];
        }
        else if(AorB==2)
        {
            winnerColor=finalColors[1];
        }
        finishPick();
    }
}