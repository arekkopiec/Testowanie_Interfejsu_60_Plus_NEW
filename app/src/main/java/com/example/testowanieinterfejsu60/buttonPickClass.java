package com.example.testowanieinterfejsu60;

public class buttonPickClass {

    public static int numberOfChoices=4;
    public static String defaultText="Przycisk";

    public static int buttonShape[]={R.drawable.round_btn, R.drawable.square_btn};

    public static int buttonShapeStyle[][]={
            {R.drawable.round_btn, R.drawable.round_btn_wb},
            {R.drawable.square_btn, R.drawable.square_btn_wb}
    };
    public static String colorsToShapeStyle[]={"#FFFFFF","#6200EE"};

    public static int buttonTextStyle[]={R.string.button_text_normal,R.string.button_text_bold};

    public static boolean butttonTextCaps[]={true, false};
}
