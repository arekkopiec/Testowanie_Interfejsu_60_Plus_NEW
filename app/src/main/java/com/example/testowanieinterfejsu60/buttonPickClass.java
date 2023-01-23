package com.example.testowanieinterfejsu60;

/**
 * Klasa, która służy do przechowywania danych używanych do przeprowadzania testu “Wybór przycisku”.
 */
public class buttonPickClass {

    /**
     * zmienna przechowująca liczbę etapów testu “Wybór Przycisku”
     */
    public static int numberOfChoices=4;
    /**
     * zmienna przechowująca domyślny tekst wyświetlany na przyciskach w teście
     */
    public static String defaultText="Przycisk";
    /**
     * jednowymiarowa tablica przechowująca pliki graficzne modyfikujące kształt przycisków
     */
    public static int buttonShape[]={R.drawable.round_btn, R.drawable.square_btn};
    /**
     * jednowymiarowa tablica przechowująca pliki graficzne modyfikujące kształt i tło przycisku
     */
    public static int buttonShapeStyle[][]={
            {R.drawable.round_btn, R.drawable.round_btn_wb},
            {R.drawable.square_btn, R.drawable.square_btn_wb}
    };

    public static String colorsToShapeStyle[]={"#FFFFFF","#6200EE"};
    /**
     * jednowymiarowa tablica przechowująca zmienne modyfikujące styl tekstu wyświetlanego w przyciskach
     */
    public static int buttonTextStyle[]={R.string.button_text_normal,R.string.button_text_bold};
    /**
     * jednowymiarowa tablica przechowująca zmienne logiczne modyfikujące sposób wyświetlanego tekstu (duże litery lub małe litery)
     */
    public static boolean butttonTextCaps[]={true, false};
}
