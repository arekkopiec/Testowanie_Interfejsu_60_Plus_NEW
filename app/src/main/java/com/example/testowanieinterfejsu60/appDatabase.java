package com.example.testowanieinterfejsu60;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class appDatabase extends SQLiteOpenHelper
{
    public Context context;
    public static final String DATABASE_NAME="interfaceTestsDatabase.db";
    public static final int DATABASE_VERSION=16;

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PROFILE_ID = "idProfile";

    public static final String TABLE_NAME_PROFILE="profile";
    public static final String SEX="sex";
    public static final String AGE_RANGE="ageRange";

    public static final String TABLE_NAME_ICON = "iconQuiz";
    public static final String COLUMN_SCORE = "finalScore";
    public static final String Q1 = "question1";
    public static final String Q2 = "question2";
    public static final String Q3 = "question3";
    public static final String Q4 = "question4";
    public static final String Q5 = "question5";
    public static final String Q6 = "question6";
    public static final String Q7 = "question7";
    public static final String Q8 = "question8";
    public static final String Q9 = "question9";
    public static final String Q10 = "question10";

    public static final String TABLE_NAME_TEXT = "textAdjustment";
    public static final String COLUMN_ADJUSTED_TEXT = "fontSize";

    public static final String TABLE_NAME_COLORS = "colorPick";
    public static final String COLUMN_COLOR_PAIR = "picked";

    public static final String TABLE_NAME_BUTTON = "buttonPick";
    public static final String COLUMN_BUTTON_PAIR_1 = "pick1";
    public static final String COLUMN_BUTTON_PAIR_2 = "pick2";
    public static final String COLUMN_BUTTON_PAIR_3 = "pick3";
    public static final String COLUMN_BUTTON_PAIR_4 = "pick4";

    public appDatabase(@Nullable Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query_profile =
                "CREATE TABLE "+ TABLE_NAME_PROFILE +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        SEX + " TEXT, " +
                        AGE_RANGE + " TEXT);";
        db.execSQL(query_profile);

        String query_quiz =
                "CREATE TABLE " + TABLE_NAME_ICON +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_PROFILE_ID + " INTEGER, " +
                        COLUMN_SCORE + " INTEGER, " +
                        Q1 + " INTEGER, " +
                        Q2 + " INTEGER, " +
                        Q3 + " INTEGER, " +
                        Q4 + " INTEGER, " +
                        Q5 + " INTEGER, " +
                        Q6 + " INTEGER, " +
                        Q7 + " INTEGER, " +
                        Q8 + " INTEGER, " +
                        Q9 + " INTEGER, " +
                        Q10 + " INTEGER);";
        db.execSQL(query_quiz);

        String query_text =
                "CREATE TABLE " + TABLE_NAME_TEXT +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_PROFILE_ID + " INTEGER, " +
                        COLUMN_ADJUSTED_TEXT + " INTEGER);";
        db.execSQL(query_text);

        String query_colors =
                "CREATE TABLE " + TABLE_NAME_COLORS +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_PROFILE_ID + " INTEGER, " +
                        COLUMN_COLOR_PAIR + " INTEGER);";
        db.execSQL(query_colors);

        String query_button =
                "CREATE TABLE " + TABLE_NAME_BUTTON +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_PROFILE_ID + " INTEGER, " +
                        COLUMN_BUTTON_PAIR_1 + " INTEGER, " +
                        COLUMN_BUTTON_PAIR_2 + " INTEGER, " +
                        COLUMN_BUTTON_PAIR_3 + " INTEGER, " +
                        COLUMN_BUTTON_PAIR_4 + " INTEGER);";
        db.execSQL(query_button);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PROFILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ICON);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TEXT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_COLORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_BUTTON);
        onCreate(db);
    }

    void addProfile(char sexSymbol, String ageRange)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SEX, String.valueOf(sexSymbol));
        cv.put(AGE_RANGE, ageRange);
        long result = db.insert(TABLE_NAME_PROFILE, null, cv);
        if(result == -1)
        {
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Added succesfully!", Toast.LENGTH_SHORT).show();
        }
    }

    void addIconQuiz(int finalScore, int[] q)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Cursor profileID = db.rawQuery("SELECT _id FROM " + TABLE_NAME_PROFILE, null);
        int selectedID=0;
        if (profileID.moveToFirst())
        {
            do
            {
                selectedID=profileID.getInt(0);
            } while (profileID.moveToNext());
        }
        cv.put(COLUMN_PROFILE_ID, selectedID);
        cv.put(COLUMN_SCORE, finalScore);
        cv.put(Q1, q[0]);
        cv.put(Q2, q[1]);
        cv.put(Q3, q[2]);
        cv.put(Q4, q[3]);
        cv.put(Q5, q[4]);
        cv.put(Q6, q[5]);
        cv.put(Q7, q[6]);
        cv.put(Q8, q[7]);
        cv.put(Q9, q[8]);
        cv.put(Q10, q[9]);
        long result = db.insert(TABLE_NAME_ICON, null, cv);
        if(result == -1)
        {
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Added succesfully!", Toast.LENGTH_SHORT).show();
        }
    }

    void addTextAdjustment(int fontSize)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Cursor profileID = db.rawQuery("SELECT _id FROM " + TABLE_NAME_PROFILE, null);
        int selectedID=0;
        if (profileID.moveToFirst())
        {
            do
            {
                selectedID=profileID.getInt(0);
            } while (profileID.moveToNext());
        }
        cv.put(COLUMN_PROFILE_ID, selectedID);
        cv.put(COLUMN_ADJUSTED_TEXT, fontSize);
        long result = db.insert(TABLE_NAME_TEXT, null, cv);
        if(result == -1)
        {
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Added succesfully!", Toast.LENGTH_SHORT).show();
        }
    }

    void addButtonPick(int buttonPair1, int buttonPair2, int buttonPair3, int buttonPair4)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Cursor profileID = db.rawQuery("SELECT _id FROM " + TABLE_NAME_PROFILE, null);
        int selectedID=0;
        if (profileID.moveToFirst())
        {
            do
            {
                selectedID=profileID.getInt(0);
            } while (profileID.moveToNext());
        }
        cv.put(COLUMN_PROFILE_ID, selectedID);
        cv.put(COLUMN_BUTTON_PAIR_1, buttonPair1);
        cv.put(COLUMN_BUTTON_PAIR_2, buttonPair2);
        cv.put(COLUMN_BUTTON_PAIR_3, buttonPair3);
        cv.put(COLUMN_BUTTON_PAIR_4, buttonPair4);
        long result = db.insert(TABLE_NAME_BUTTON, null, cv);
        if(result == -1)
        {
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Added succesfully!", Toast.LENGTH_SHORT).show();
        }
    }

    void addColorPick(int colorPair)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Cursor profileID = db.rawQuery("SELECT _id FROM " + TABLE_NAME_PROFILE, null);
        int selectedID=0;
        if (profileID.moveToFirst())
        {
            do
            {
                selectedID=profileID.getInt(0);
            } while (profileID.moveToNext());
        }
        cv.put(COLUMN_PROFILE_ID, selectedID);
        cv.put(COLUMN_COLOR_PAIR, colorPair);
        long result = db.insert(TABLE_NAME_COLORS, null, cv);
        if(result == -1)
        {
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Added succesfully!", Toast.LENGTH_SHORT).show();
        }
    }

    void generateReportFromDB()
    {
         SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyy_HH_mm_ss");
         Date date = new Date();
         String generatedFileName = "TestowanieInterfejsu_Raport_" + formatter.format(date) + ".txt";

        File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        root = new File(root, generatedFileName);
        FileOutputStream workingFile = null;
        String selectedIconData;
        String selectedTextData;
        String selectedColorData;
        String selectedButtonData;
        String selectedProfileData;
        String profileList = "Stworzone profile:; \n";
        String profileColumns = COLUMN_ID + ";" + SEX + ";" + AGE_RANGE + ";";
        String iconQuizText = "Wyniki testów Quizu Ikon:; \n";
        String iconQuizColumns = COLUMN_ID + ";" + COLUMN_PROFILE_ID + ";" + COLUMN_SCORE + ";" + Q1 + ";" + Q2 + ";" + Q3 + ";" + Q4 + ";" + Q5 + ";" + Q6 + ";" + Q7 + ";" + Q8 + ";" + Q9 + ";" + Q10 + ";" ;
        String textAdjustText = "Wyniki testów Czcionki:; \n";
        String textAdjustColumns = COLUMN_ID + ";" + COLUMN_PROFILE_ID + ";" + COLUMN_ADJUSTED_TEXT + ";";
        String colorPickText = "Wyniki testów Kolorystyki:; \n";
        String colorPickColumns = COLUMN_ID + ";" + COLUMN_PROFILE_ID + ";" + COLUMN_COLOR_PAIR + ";";
        String buttonPickText = "Wyniki testów Wyybotu Guzika:; \n";
        String buttonPickColumns = COLUMN_ID + ";" + COLUMN_PROFILE_ID + ";" + COLUMN_BUTTON_PAIR_1 + ";" + COLUMN_BUTTON_PAIR_2 + ";" + COLUMN_BUTTON_PAIR_3 + ";" + COLUMN_BUTTON_PAIR_4 + ";";
        String spaceInBetween = ";";
        String breakALine ="\n";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor profileData = db.rawQuery("SELECT * FROM " + TABLE_NAME_PROFILE, null);
        Cursor iconData = db.rawQuery("SELECT * FROM " + TABLE_NAME_ICON, null);
        Cursor textData = db.rawQuery("SELECT * FROM " + TABLE_NAME_TEXT, null);
        Cursor colorData = db.rawQuery("SELECT * FROM " + TABLE_NAME_COLORS,null);
        Cursor buttonData = db.rawQuery("SELECT * FROM " + TABLE_NAME_BUTTON, null);

            try
            {
                workingFile = new FileOutputStream(root);

                workingFile.write(profileList.getBytes());
                workingFile.write(profileColumns.getBytes());
                workingFile.write(breakALine.getBytes());
                if (profileData.moveToFirst())
                {
                    do
                    {
                        for (int i=0; i<profileData.getColumnCount(); i++) {
                            selectedProfileData = profileData.getString(i);
                            workingFile.write(selectedProfileData.getBytes());
                            workingFile.write(spaceInBetween.getBytes());
                        }

                        workingFile.write(breakALine.getBytes());
                    } while (profileData.moveToNext());
                }

                workingFile.write(breakALine.getBytes());

                workingFile.write(iconQuizText.getBytes());
                workingFile.write(iconQuizColumns.getBytes());
                workingFile.write(breakALine.getBytes());
                if (iconData.moveToFirst())
                {
                    do
                    {
                        for (int i=0; i<iconData.getColumnCount(); i++) {
                            selectedIconData = iconData.getString(i);
                            workingFile.write(selectedIconData.getBytes());
                            workingFile.write(spaceInBetween.getBytes());
                        }

                        workingFile.write(breakALine.getBytes());
                    } while (iconData.moveToNext());
                }

                workingFile.write(breakALine.getBytes());


                workingFile.write(textAdjustText.getBytes());
                workingFile.write(textAdjustColumns.getBytes());
                workingFile.write(breakALine.getBytes());
                if (textData.moveToFirst())
                {
                    do
                    {
                        for (int i=0; i<textData.getColumnCount(); i++)
                        {
                            selectedTextData = textData.getString(i);
                            workingFile.write(selectedTextData.getBytes());
                            workingFile.write(spaceInBetween.getBytes());
                        }

                        workingFile.write(breakALine.getBytes());
                    } while (textData.moveToNext());
                }

                workingFile.write(breakALine.getBytes());

                workingFile.write(colorPickText.getBytes());
                workingFile.write(colorPickColumns.getBytes());
                workingFile.write(breakALine.getBytes());
                if (colorData.moveToFirst())
                {
                    do
                    {
                        for (int i=0; i<colorData.getColumnCount(); i++)
                        {
                            selectedColorData = colorData.getString(i);
                            workingFile.write(selectedColorData.getBytes());
                            workingFile.write(spaceInBetween.getBytes());
                        }

                        workingFile.write(breakALine.getBytes());
                    } while (colorData.moveToNext());
                }

                workingFile.write(breakALine.getBytes());

                workingFile.write(buttonPickText.getBytes());
                workingFile.write(buttonPickColumns.getBytes());
                workingFile.write(breakALine.getBytes());
                if (buttonData.moveToFirst())
                {
                    do
                    {
                        for (int i=0; i<buttonData.getColumnCount(); i++)
                        {
                            selectedButtonData = buttonData.getString(i);
                            workingFile.write(selectedButtonData.getBytes());
                            workingFile.write(spaceInBetween.getBytes());
                        }

                        workingFile.write(breakALine.getBytes());
                    } while (buttonData.moveToNext());
                }

                Toast.makeText(context, "Wygenerowano raport!", Toast.LENGTH_LONG).show();
            }
            catch (FileNotFoundException e)
             {
                e.printStackTrace();
             }
            catch (IOException e)
             {
                e.printStackTrace();
             }
            finally
            {
                if (workingFile != null)
                {
                    try
                    {
                        workingFile.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

        }
}