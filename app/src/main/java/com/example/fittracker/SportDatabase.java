package com.example.fittracker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SportDataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sport.db";
    private static final String TABLE_NAME = "sport";

    private static final String COL1 = "ID";
    private static final String COL2 = "NOM";
    private static final String COL3 = "BURNEDCAL_H";

    public SportDataBase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    //creation de la base et insertion des données
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT, " +
                COL3 + " REAL)";
        db.execSQL(createTable);

        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Course à pied', 500)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Natation', 420)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Vélo', 380)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Marche rapide', 250)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Musculation', 320)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Danse', 330)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Water polo', 470)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Marche', 200)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Boxe', 600)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Zumba', 420)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('HIIT', 700)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Yoga', 200)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Pilates', 230)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Basketball', 500)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Football', 600)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Tennis', 520)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    //récupération de tous les noms
    public ArrayList<String> getAllNames() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COL2 + " FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0)); // 0 = première colonne (NOM)
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }
    //récupérer les calories à partir de nom choisit
    public double getCaloriesByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT " + COL3 + " FROM " + TABLE_NAME + " WHERE NOM = ?",
                new String[]{name}
        );

        double calories = 0;

        if (cursor.moveToFirst()) {
            calories = cursor.getDouble(0);
        }

        cursor.close();
        return calories;
    }
}
