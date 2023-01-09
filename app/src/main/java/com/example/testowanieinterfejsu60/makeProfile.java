package com.example.testowanieinterfejsu60;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class makeProfile extends AppCompatActivity
{
    RadioGroup radioS;
    RadioButton radioBM, radioBF;
    Button cancelTest, sendValue, chosenSex;
    String ageString;
    char S='K';
    Spinner ageSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_profile);

        radioS = findViewById(R.id.radio_s);
        ageSelect= findViewById(R.id.age_spinner);
        String[] ageItems = new String[]{"60-64","65-69","70-74","75-79","80-84","85-89","90+"};
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ageItems);
        ageSelect.setAdapter(ageAdapter);

        radioBM=findViewById(R.id.sex_male);
        radioBM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                S='M';
            }
        });

        radioBF=findViewById(R.id.sex_female);
        radioBF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                S='K';
            }
        });

        cancelTest = findViewById(R.id.cancel_profile);
        cancelTest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                cancelProfile();
            }
        });

        sendValue=findViewById(R.id.send_profile);
        sendValue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finishProfile();
            }
        });
    }

    public void checkButton(View v)
    {

    }

    void finishProfile()
    {
        ageString = ageSelect.getSelectedItem().toString();
        appDatabase myDB = new appDatabase(makeProfile.this);
        myDB.addProfile(S, ageString);
        new AlertDialog.Builder(this)
                .setTitle("Profil utworzony")
                .setMessage("Można przystąpić do testów interfejsu.")
                .setPositiveButton("Zakończ",((dialogInterface, i) -> backToMain()))
                .setCancelable(false)
                .show();

    }

    void cancelProfile()
    {
        new AlertDialog.Builder(this)
                .setTitle("Anulować tworzenie profilu?")
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