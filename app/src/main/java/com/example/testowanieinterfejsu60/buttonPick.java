package com.example.testowanieinterfejsu60;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Klasa buttonPick służy do wyświetlania i przeprowadzania testu “Wybór Przycisku”.
 */
public class buttonPick extends AppCompatActivity
{
    LinearLayout chosenA, chosenB;
    Button cancelTest, sendValue;
    Button optionA, optionB;

    /**
     * zmienna określająca na, którym etapie testu “Wybór Przycisku” znajduje się użytkownik
     */
    int currentPair=0;
    int numberOfPairs=(buttonPickClass.numberOfChoices);
    /**
     * tablica przechowująca wybory użytkownika dokonane podczas testu “Wybór Przycisku”
     */
    int chosenButton[]=new int[numberOfPairs];
    boolean isSomethingChosen=false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_pick);

        chosenA = findViewById(R.id.color_option_A);
        chosenB = findViewById(R.id.color_option_B);

        optionA = findViewById(R.id.button_option_A);
        optionA.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chosenB.setBackgroundColor(Color.parseColor("#FFFFFF"));
                chosenButton[currentPair]=0;
                isSomethingChosen=true;
                chosenA.setBackgroundColor(Color.parseColor("#32CD32"));
            }
        });

        optionB = findViewById(R.id.button_option_B);
        optionB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chosenA.setBackgroundColor(Color.parseColor("#FFFFFF"));
                chosenButton[currentPair]=1;
                isSomethingChosen=true;
                chosenB.setBackgroundColor(Color.parseColor("#32CD32"));
            }
        });

        loadNextPair();

        cancelTest=findViewById(R.id.cancel_button_pick);
        cancelTest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                cancelCurrentTest();
            }
        });

        sendValue=findViewById(R.id.send_button_pick);
        sendValue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               checkIfChosen();
            }
        });
    }

    void loadNextPair() //funkcja, której zadaniem jest przechodzenie i wyświetlenie następnego etapu testu “Wybór Przycisku”
    {
        isSomethingChosen=false;
        switch (currentPair)
        {
            case 0:
                chosenA.setBackgroundColor(Color.parseColor("#FFFFFF"));
                chosenB.setBackgroundColor(Color.parseColor("#FFFFFF"));

                optionA.setBackgroundResource(buttonPickClass.buttonShape[0]);
                optionA.setText(buttonPickClass.defaultText);
                optionA.setTextColor(Color.parseColor("#FFFFFF"));
                optionB.setBackgroundResource(buttonPickClass.buttonShape[1]);
                optionB.setText(buttonPickClass.defaultText);
                optionB.setTextColor(Color.parseColor("#FFFFFF"));
                break;

            case 1:
                chosenA.setBackgroundColor(Color.parseColor("#FFFFFF"));
                chosenB.setBackgroundColor(Color.parseColor("#FFFFFF"));

                optionA.setBackgroundResource(buttonPickClass.buttonShapeStyle[chosenButton[0]][0]);
                optionA.setTextColor(Color.parseColor(String.valueOf(buttonPickClass.colorsToShapeStyle[0])));
                optionB.setBackgroundResource(buttonPickClass.buttonShapeStyle[chosenButton[0]][1]);
                optionB.setTextColor(Color.parseColor(String.valueOf(buttonPickClass.colorsToShapeStyle[1])));
                break;

            case 2:
                chosenA.setBackgroundColor(Color.parseColor("#FFFFFF"));
                chosenB.setBackgroundColor(Color.parseColor("#FFFFFF"));

                optionA.setTextColor(Color.parseColor(String.valueOf(buttonPickClass.colorsToShapeStyle[chosenButton[1]])));
                optionA.setBackgroundResource(buttonPickClass.buttonShapeStyle[chosenButton[0]][chosenButton[1]]);
                optionB.setTextColor(Color.parseColor(String.valueOf(buttonPickClass.colorsToShapeStyle[chosenButton[1]])));
                optionB.setBackgroundResource(buttonPickClass.buttonShapeStyle[chosenButton[0]][chosenButton[1]]);

                optionA.setText(buttonPickClass.buttonTextStyle[0]);
                optionB.setText(buttonPickClass.buttonTextStyle[1]);
                break;

            case 3:
                chosenA.setBackgroundColor(Color.parseColor("#FFFFFF"));
                chosenB.setBackgroundColor(Color.parseColor("#FFFFFF"));

                optionA.setText(buttonPickClass.buttonTextStyle[chosenButton[2]]);
                optionB.setText(buttonPickClass.buttonTextStyle[chosenButton[2]]);

                optionA.setAllCaps(buttonPickClass.butttonTextCaps[0]);
                optionB.setAllCaps(buttonPickClass.butttonTextCaps[1]);
                break;

            case 4:
                finishPick();
                return;
        }
    }

    void finishPick() //funkcja, która wysyła dane do bazy danych, później wyświetla okno dialogowe o zakończeniu testu wraz z przyciskiem zamykającym test “Wybór Przycisku”
    {
        appDatabase myDB = new appDatabase(buttonPick.this);
        myDB.addButtonPick(chosenButton[0], chosenButton[1], chosenButton[2], chosenButton[3]);
        new AlertDialog.Builder(this)
                .setTitle("Koniec")
                .setMessage("Dane zostały przesłane do bazy.")
                .setPositiveButton("Zakończ",((dialogInterface, i) -> backToMain()))
                .setCancelable(false)
                .show();
    }

    void checkIfChosen() //funkcja sprawdzająca czy użytkownik dokonał wyboru w teście “Wybór Przycisku” i wyświetlająca odpowiedni komunikat jeśli tego nie dokonał
    {
        if(isSomethingChosen==true)
        {
            currentPair++;
            loadNextPair();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Nie zaznaczono odpowiedzi.",Toast.LENGTH_SHORT).show();
        }
    }

    void cancelCurrentTest() //funkcja tworząca okno dialogowe pozwalające na zamknięcie testu “Wybór Przycisku” lub rezygnację z zamknięcia
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
    } //funkcja zamykająca test “Wybór Przycisku”
}