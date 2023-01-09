package com.example.testowanieinterfejsu60;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

public class iconQuestion
{
    public static int image[]= {R.drawable.ic_baseline_info_quiz_24,
            R.drawable.ic_baseline_home_quiz_24,
            R.drawable.ic_baseline_settings_quiz_24,
            R.drawable.ic_baseline_share_quiz_24,
            R.drawable.ic_baseline_refresh_quiz_24,
            R.drawable.ic_baseline_mail_quiz_24,
            R.drawable.ic_baseline_search_quiz_24,
            R.drawable.ic_baseline_language_quiz_24,
            R.drawable.ic_baseline_save_quiz_24,
            R.drawable.ic_baseline_location_quiz_24
    };

    public static String question="Co przedstawia następująca ikonka";

    public static String answers[][]=
            {
                      {"Lista aplikacji","Przycisk domowy","Informacje"},
                      {"Przycisk domowy","Przycisk powrotu","Lista aplikacji"},
                      {"Informacje","Wyciszenie","Ustawienia"},
                      {"Powiadomienia","Udostępnij","Lokalizacja"},
                      {"Odśwież","Przycisk powrotu","Obróć"},
                      {"Chat","Poczta","Powiadomienia"},
                      {"Powiększ","Szukaj","Pomniejsz"},
                      {"Mapy","Lokalizacja","Język"},
                      {"Zapisz","Menedżer plików","Galeria"},
                      {"Przypnij","Mapy","Lokalizacja"}
            };

    public static String answersCorrect[]=
            {
                    "Informacje",
                    "Przycisk domowy",
                    "Ustawienia",
                    "Udostępnij",
                    "Odśwież",
                    "Poczta",
                    "Szukaj",
                    "Język",
                    "Zapisz",
                    "Lokalizacja"
            };

}
