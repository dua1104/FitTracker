package com.example.fittracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

class FoodDataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "food.db";
    private static final int DATABASE_VERSION = 2; // increment for schema changes

    // Food table
    private static final String FOOD_TABLE = "food";
    private static final String FOOD_COL_ID = "ID";
    private static final String FOOD_COL_NAME = "NOM";
    private static final String FOOD_COL_CAL = "CONSUMEDCAL_G";

    // User meals table
    private static final String MEAL_TABLE = "meals";
    private static final String MEAL_COL_ID = "ID";
    private static final String MEAL_COL_FOOD = "FOOD_NAME";
    private static final String MEAL_COL_QTY = "QUANTITY";
    private static final String MEAL_COL_TYPE = "MEAL_TYPE";

    public FoodDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create food table
        String createFoodTable = "CREATE TABLE " + FOOD_TABLE + " (" +
                FOOD_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FOOD_COL_NAME + " TEXT, " +
                FOOD_COL_CAL + " REAL)";
        db.execSQL(createFoodTable);

        // Insert default foods
        db.execSQL("INSERT INTO " + FOOD_TABLE + " (" + FOOD_COL_NAME + "," + FOOD_COL_CAL + ") VALUES ('Pomme', 52)");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " (" + FOOD_COL_NAME + "," + FOOD_COL_CAL + ") VALUES ('Banane', 89)");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " (" + FOOD_COL_NAME + "," + FOOD_COL_CAL + ") VALUES ('Orange', 47)");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " (" + FOOD_COL_NAME + "," + FOOD_COL_CAL + ") VALUES ('Carotte', 41)");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " (" + FOOD_COL_NAME + "," + FOOD_COL_CAL + ") VALUES ('Tomate', 18)");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " (" + FOOD_COL_NAME + "," + FOOD_COL_CAL + ") VALUES ('Poulet grillé', 165)");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " (" + FOOD_COL_NAME + "," + FOOD_COL_CAL + ") VALUES ('Bœuf', 250)");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " (" + FOOD_COL_NAME + "," + FOOD_COL_CAL + ") VALUES ('Œuf', 155)");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " (" + FOOD_COL_NAME + "," + FOOD_COL_CAL + ") VALUES ('Yaourt nature', 59)");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " (" + FOOD_COL_NAME + "," + FOOD_COL_CAL + ") VALUES ('Fromage blanc', 98)");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " (" + FOOD_COL_NAME + "," + FOOD_COL_CAL + ") VALUES ('Riz cuit', 130)");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " (" + FOOD_COL_NAME + "," + FOOD_COL_CAL + ") VALUES ('Pâtes cuites', 131)");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " (" + FOOD_COL_NAME + "," + FOOD_COL_CAL + ") VALUES ('Pain', 265)");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " (" + FOOD_COL_NAME + "," + FOOD_COL_CAL + ") VALUES ('Chips', 536)");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " (" + FOOD_COL_NAME + "," + FOOD_COL_CAL + ") VALUES ('Chocolat', 546)");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " (" + FOOD_COL_NAME + "," + FOOD_COL_CAL + ") VALUES ('Jus d’orange', 45)");

        // Create meals table
        String createMealTable = "CREATE TABLE " + MEAL_TABLE + " (" +
                MEAL_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MEAL_COL_FOOD + " TEXT, " +
                MEAL_COL_QTY + " REAL, " +
                MEAL_COL_TYPE + " TEXT)";
        db.execSQL(createMealTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FOOD_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MEAL_TABLE);
        onCreate(db);
    }

    // Get all food names
    public ArrayList<String> getAllNames() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + FOOD_COL_NAME + " FROM " + FOOD_TABLE, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // Get calories per 100g
    public double getCaloriesByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT " + FOOD_COL_CAL + " FROM " + FOOD_TABLE + " WHERE LOWER(" + FOOD_COL_NAME + ") = ?",
                new String[]{name.toLowerCase()}
        );
        double calories = 0;
        if (cursor.moveToFirst()) {
            calories = cursor.getDouble(0);
        }
        cursor.close();
        return calories;
    }


    // Add a meal
    public boolean addMeal(String foodName, double quantity, String mealType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MEAL_COL_FOOD, foodName);
        cv.put(MEAL_COL_QTY, quantity);
        cv.put(MEAL_COL_TYPE, mealType);
        long result = db.insert(MEAL_TABLE, null, cv);
        return result != -1;
    }
}
