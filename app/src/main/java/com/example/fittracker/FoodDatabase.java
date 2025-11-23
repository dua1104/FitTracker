package com.example.fittracker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class FoodDataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "food.db";
    private static final String TABLE_NAME = "food";

    private static final String COL1 = "ID";
    private static final String COL2 = "NOM";
    private static final String COL3 = "CONSUMEDCAL_G";

    public FoodDataBase(Context context) {
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

        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Pomme', 52)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Banane', 89)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Orange', 47)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Carotte', 41)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Tomate', 18)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Poulet grillé', 165)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Bœuf', 250)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Œuf', 155)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Yaourt nature', 59)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Fromage blanc', 98)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Riz cuit', 130)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Pâtes cuites', 131)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Pain', 265)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Chips', 536)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Chocolat', 546)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ", " + COL3 + ") VALUES ('Jus d’orange', 45)");
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
