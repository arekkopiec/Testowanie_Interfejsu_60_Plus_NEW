package com.example.testowanieinterfejsu60;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {
    CardView modifyProfile, iconQuiz,textAdjust,colorPick,buttonPick,finishTesting;
    Button createReport, closeApp;
    public boolean wasAProfileCreated=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);

        instructionMessage();

        modifyProfile=findViewById(R.id.profile);
        modifyProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                wasAProfileCreated=true;
                openProfile();
            }
        });

        iconQuiz=findViewById(R.id.icon_quiz);
        iconQuiz.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
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

    void openProfile()
    {
        Intent intent=new Intent(MainActivity.this,makeProfile.class);
        startActivity(intent);
    }

    void openIconQuiz()
    {
        Intent intent=new Intent(MainActivity.this,iconQuiz.class);
        startActivity(intent);
    }

    void openTextAdjustment()
    {
        Intent intent=new Intent(MainActivity.this,textAdjustment.class);
        startActivity(intent);
    }

    void openColorPick()
    {
        Intent intent=new Intent(MainActivity.this,colorPick.class);
        startActivity(intent);
    }

    void openButtonPick()
    {
        Intent intent=new Intent(MainActivity.this,buttonPick.class);
        startActivity(intent);
    }

    void generateReportFromDatabase()
    {
        appDatabase myDB = new appDatabase(MainActivity.this);
        myDB.generateReportFromDB();
    }

    void closeApp()
    {
        new AlertDialog.Builder(this)
                .setTitle("Wyłączyć aplikację?")
                .setPositiveButton("Tak",(dialogInterface, i) -> finish())
                .setNegativeButton("Nie",null)
                .show();
    }

    void instructionMessage()
    {
        new AlertDialog.Builder(this)
                .setTitle("Przebieg testowania")
                .setMessage("Przed rozpoczęciem testów należy wejść do 'Profil' w celu utworzenia nowego profilu użytkownika poprzez podanie Pańskiej płci oraz przedziału wiekowego. \n\nPo utworzeniu profilu można przystąpić do kilku różnych testów, które będą sprawdzać Państwa preferencje dotyczące interfejsu mobilnego.")
                .setPositiveButton("ZAMKNIJ KOMUNIKAT",null)
                .show();
    }

    void noProfileCreated()
    {
        new AlertDialog.Builder(this)
                .setTitle("Nie stworzono profilu!")
                .setPositiveButton("UTWÓRZ",(dialogInterface, i) -> forcedProfile())
                .show();
    }

    void forcedProfile()
    {
        wasAProfileCreated=true;
        Intent intent=new Intent(MainActivity.this,makeProfile.class);
        startActivity(intent);
    }

    void thankYouMessage()
    {
        new AlertDialog.Builder(this)
                .setTitle("Dziękujęmy za wykonanie testów")
                .setMessage("Komunikat można zamknąć i odłożyć telefon, aby następna osoba mogła przystąpić do testu.")
                .setPositiveButton("ZAMKNIJ KOMUNIKAT", (dialogInterface, i) -> instructionMessage())
                .show();
    }

    void closeProfile()
    {
        wasAProfileCreated=false;
        thankYouMessage();
    }

}