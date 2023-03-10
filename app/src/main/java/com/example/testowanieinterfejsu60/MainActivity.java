package com.example.testowanieinterfejsu60;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Klasa mainActivity służy do wyświetlania strony głównej aplikacji w, której wyświetlane są przyciski uruchamiające testy, kreator profilu oraz
 * generator raportu z bazy danych.
 */
public class MainActivity extends AppCompatActivity {
    CardView modifyProfile, iconQuiz,textAdjust,colorPick,buttonPick,finishTesting;
    Button createReport, closeApp;
    /**
     * zmienna logiczna określająca czy został w danej sesji stworzony profil co jest konieczne, aby uzyskać dostęp do uruchomienia testów
     */
    public boolean wasAProfileCreated=false;

    public static final String SHARED_PREFERENCE = "sharedPref";
    public static final String isCreated = "profil";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);

        resetOnOpen();
        instructionMessage();

        modifyProfile=findViewById(R.id.profile);
        modifyProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openProfile();
            }
        });

        iconQuiz=findViewById(R.id.icon_quiz);
        iconQuiz.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                loadProfileState();
                if (wasAProfileCreated==true)
                {
                   openIconQuiz();
                }
                else
                {
                    noProfileCreated();
                }
            }
        });

        textAdjust=findViewById(R.id.text_adjust);
        textAdjust.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                loadProfileState();
                if (wasAProfileCreated==true)
                {
                    openTextAdjustment();
                }
                else
                {
                    noProfileCreated();
                }
            }
        });

        colorPick=findViewById(R.id.color_pick);
        colorPick.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                loadProfileState();
                if (wasAProfileCreated==true)
                {
                    openColorPick();
                }
                else
                {
                    noProfileCreated();
                }
            }
        });

        buttonPick=findViewById(R.id.button_pick);
        buttonPick.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                loadProfileState();
                if (wasAProfileCreated==true)
                {
                    openButtonPick();
                }
                else
                {
                    noProfileCreated();
                }
            }
        });

        finishTesting=findViewById(R.id.finish_tests);
        finishTesting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                closeProfile();
            }
        });

        //
        closeApp=findViewById(R.id.close_app);
        closeApp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                closeApp();
            }
        });

        createReport=findViewById(R.id.report_button);
        createReport.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                    generateReportFromDatabase();
            }
        });
    }

    /**
     * funkcja uruchamiająca kreator profilu
     */
    void openProfile() //funkcja uruchamiająca kreator profilu
    {
        Intent intent=new Intent(MainActivity.this,makeProfile.class);
        startActivity(intent);
    }

    /**
     * funkcja uruchamiająca test “Quiz Ikon”
     */
    void openIconQuiz() //funkcja uruchamiająca test “Quiz Ikon”
    {
        Intent intent=new Intent(MainActivity.this,iconQuiz.class);
        startActivity(intent);
    }

    /**
     * funkcja uruchamiająca test “Dostosowywanie Czcionki”
     */
    void openTextAdjustment() //funkcja uruchamiająca test “Dostosowywanie Czcionki”
    {
        Intent intent=new Intent(MainActivity.this,textAdjustment.class);
        startActivity(intent);
    }

    /**
     * funkcja uruchamiająca test “Kolorystyka”
     */
    void openColorPick() //funkcja uruchamiająca test “Kolorystyka”
    {
        Intent intent=new Intent(MainActivity.this,colorPick.class);
        startActivity(intent);
    }

    /**
     * funkcja uruchamiająca test “Wybór Przycisku”
     */
    void openButtonPick() //funkcja uruchamiająca test “Wybór Przycisku”
    {
        Intent intent=new Intent(MainActivity.this,buttonPick.class);
        startActivity(intent);
    }

    /**
     * funkcja tworząca nową zmienną typu appDatabase do obsługi danych zapisanych w bazie  danych oraz wywołanie wewnętrznej funkcji  do tworzenia raportu z bazy danych
     */
    void generateReportFromDatabase() //funkcja tworząca nową zmienną typu appDatabase do obsługi danych zapisanych w bazie  danych oraz wywołanie wewnętrznej funkcji  do tworzenia raportu z bazy danych
    {
        appDatabase myDB = new appDatabase(MainActivity.this);
        myDB.generateReportFromDB();
    }

    /**
     * funkcja tworząca okno dialogowe pozwalające na zamknięcie aplikacji lub rezygnacji z zamknięcia. Do zamknięcia aplikacji wykorzystywana jest wbudowana w języku Java funkcja finish()
     */
    void closeApp() //funkcja tworząca okno dialogowe pozwalające na zamknięcie aplikacji lub rezygnacji z zamknięcia. Do zamknięcia aplikacji wykorzystywana jest wbudowana w języku Java funkcja finish()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(isCreated, false);
        editor.apply();

        new AlertDialog.Builder(this)
                .setTitle("Wyłączyć aplikację?")
                .setPositiveButton("Tak",(dialogInterface, i) -> finish())
                .setNegativeButton("Nie",null)
                .show();
    }

    /**
     * funkcja tworząca okno dialogowe z wiadomością tekstową oraz przyciskiem do zamknięcia okna
     */
    void instructionMessage() //funkcja tworząca okno dialogowe z wiadomością tekstową oraz przyciskiem do zamknięcia okna
    {
        new AlertDialog.Builder(this)
                .setTitle("Przebieg testowania")
                .setMessage("Przed rozpoczęciem testów należy wejść do 'Profil' w celu utworzenia nowego profilu użytkownika poprzez podanie Pańskiej płci oraz przedziału wiekowego. \n\nPo utworzeniu profilu można przystąpić do kilku różnych testów, które będą sprawdzać Państwa preferencje dotyczące interfejsu mobilnego.")
                .setPositiveButton("ZAMKNIJ KOMUNIKAT",null)
                .show();
    }

    /**
     * funkcja tworząca okno dialogowe z wiadomością tekstową oraz przyciskiem otwierającym okno kreatora profilu
     */
    void noProfileCreated() //funkcja tworząca okno dialogowe z wiadomością tekstową oraz przyciskiem otwierającym okno kreatora profilu
    {
        new AlertDialog.Builder(this)
                .setTitle("Nie stworzono profilu!")
                .setPositiveButton("UTWÓRZ",(dialogInterface, i) -> forcedProfile())
                .show();
    }

    /**
     * funkcja uruchamiająca kreator profilu
     */
    void forcedProfile() //funkcja uruchamiająca kreator profilu
    {
        wasAProfileCreated=true;
        Intent intent=new Intent(MainActivity.this,makeProfile.class);
        startActivity(intent);
    }

    /**
     * funkcja tworząca okno dialogowe z wiadomością tekstową z przyciskiem wywołującym kolejne okno dialogowe z wiadomością tekstową
     */
    void thankYouMessage() //funkcja tworząca okno dialogowe z wiadomością tekstową z przyciskiem wywołującym kolejne okno dialogowe z wiadomością tekstową
    {
        new AlertDialog.Builder(this)
                .setTitle("Dziękujęmy za wykonanie testów")
                .setMessage("Komunikat można zamknąć i odłożyć telefon, aby następna osoba mogła przystąpić do testu.")
                .setPositiveButton("ZAMKNIJ KOMUNIKAT", (dialogInterface, i) -> instructionMessage())
                .show();
    }

    /**
     * funkcja ustawiająca informację, że profil nie został utworzony oraz otwierająca okno dialogowe
     */
    void closeProfile() //funkcja ustawiająca informację, że profil nie został utworzony oraz otwierająca okno dialogowe
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(isCreated, false);
        editor.apply();
        loadProfileState();
        thankYouMessage();
    }

    /**
     * funkcja wczytująca obecny stan profilu do zmiennej wasAProfileCreated
     */
    void loadProfileState() //funkcja wczytująca obecny stan profilu do zmiennej wasAProfileCreated
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE);
        wasAProfileCreated = sharedPreferences.getBoolean(isCreated, false);
    }

    /**
     * funkcja resetująca stan profilu na nie utworzony przy uruchomieniu aplikacji
     */
    void resetOnOpen()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(isCreated, false);
        editor.apply();
    }
}