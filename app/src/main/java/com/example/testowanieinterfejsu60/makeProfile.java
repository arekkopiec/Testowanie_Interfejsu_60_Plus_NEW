package com.example.testowanieinterfejsu60;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

/**
 * Klasa makeProfile służy do wyświetlania i obsługi kreatora profilu.
 */
public class makeProfile extends AppCompatActivity
{
    RadioGroup radioS;
    RadioButton radioBM, radioBF;
    Button cancelTest, sendValue, chosenSex;
    /**
     * zmienna przechowująca przedział wiekowy wybrany przez użytkownika
     */
    String ageString;
    /**
     * zmienna znakowa określająca płeć wybrana przez użytkownika
     */
    char S='K';
    Spinner ageSelect;
    boolean yes=true;

    public static final String SHARED_PREFERENCE = "sharedPref";
    public static final String isCreated = "profil";

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

    /**
     * funkcja przesyłająca dane utworzonego profilu do bazy danych i zamykająca kreator profilu
     */
    void finishProfile() //
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(isCreated, yes);
        editor.apply();

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

    /**
     * funkcja anulująca tworzenie profilu i zamykająca kreator profilu
     */
    void cancelProfile() //
    {
        new AlertDialog.Builder(this)
                .setTitle("Anulować tworzenie profilu?")
                .setPositiveButton("Tak",((dialogInterface, i) -> backToMain()))
                .setNegativeButton("Nie",null)
                .setCancelable(true)
                .show();
    }

    /**
     * zamknięcie kreatora wywołując wbudowaną w języku Java funkcji do zamykania finish()
     */
    void backToMain()
    {
        finish();
    } //
}