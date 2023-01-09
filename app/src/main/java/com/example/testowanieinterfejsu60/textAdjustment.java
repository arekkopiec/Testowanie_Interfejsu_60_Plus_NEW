package com.example.testowanieinterfejsu60;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.TextView;
import android.util.TypedValue;

public class textAdjustment extends AppCompatActivity
{
    Button increaseText, decreaseText, sendValue, cancelTest;
    TextView textToAdjust, displaySize;
    float sizeOfText;
    float diff=3f;
    int sizeOfTextInt;
    String sizeOfTextString;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_adjustment);

        decreaseText=findViewById(R.id.decrease_text);
        increaseText=findViewById(R.id.increase_text);
        textToAdjust=findViewById(R.id.adjusted_text);
        displaySize=findViewById(R.id.display_size);

        sizeOfText=textToAdjust.getTextSize();
        sizeOfText=sizeOfText/getResources().getDisplayMetrics().scaledDensity;
        sizeOfTextInt=(int) sizeOfText;
        sizeOfTextString=String.valueOf(sizeOfTextInt);
        displaySize.setText(sizeOfTextString);

        decreaseText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                fontSizeDecrease();
            }
        });

        increaseText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                fontSizeIncrease();
            }
        });

        cancelTest=findViewById(R.id.cancel_text_adjust);
        cancelTest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                cancelCurrentTest();
            }
        });

        sendValue=findViewById(R.id.send_text_adjust);
        sendValue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                sendTextAdjust();
            }
        });
    }

    void fontSizeDecrease()
    {
        sizeOfText=textToAdjust.getTextSize();
        sizeOfText = sizeOfText-diff;
        textToAdjust.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeOfText);
        sizeOfText=sizeOfText/getResources().getDisplayMetrics().scaledDensity;
        sizeOfTextInt=(int) sizeOfText;
        sizeOfTextString=String.valueOf(sizeOfTextInt);
        displaySize.setText(sizeOfTextString);
    }

    void fontSizeIncrease()
    {
        sizeOfText=textToAdjust.getTextSize();
        sizeOfText = sizeOfText+diff;
        textToAdjust.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeOfText);
        sizeOfText=sizeOfText/getResources().getDisplayMetrics().scaledDensity;
        sizeOfTextInt=(int) sizeOfText;
        sizeOfTextString=String.valueOf(sizeOfTextInt);
        displaySize.setText(sizeOfTextString);
    }

    void sendTextAdjust()
    {
        appDatabase myDB = new appDatabase(textAdjustment.this);
        myDB.addTextAdjustment(sizeOfTextInt);
        new AlertDialog.Builder(this)
                .setTitle("Koniec")
                .setMessage("Dane zostały przesłane do bazy.")
                .setPositiveButton("Zakończ",((dialogInterface, i) -> backToMain()))
                .setCancelable(false)
                .show();
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
}