package com.example.fittracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

public class UserDataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user.db";
    private static final String TABLE_NAME = "user";

    private static final String COL1 = "ID";
    private static final String COL2 = "NOM";
    private static final String COL3 = "PRENOM";
    private static final String COL4 = "MAIL";
    private static final String COL5 = "DATENAISS";
    private static final String COL6 = "MDP";
    private static final String COL7 = "TAILLE";
    private static final String COL8 = "POIDS";

    public UserDataBase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT, " +
                COL3 + " TEXT, " +
                COL4 + " TEXT UNIQUE, " +
                COL5 + " TEXT, " +
                COL6 + " TEXT, " +
                COL7 + " REAL, " +
                COL8 + " REAL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addUser(String nom, String prenom, String mail, String mdp,
                           String datenaiss, String taille, String poids) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL2, nom);
        cv.put(COL3, prenom);
        cv.put(COL4, mail);
        cv.put(COL5, datenaiss);
        cv.put(COL6, mdp);
        cv.put(COL7, taille);
        cv.put(COL8, poids);

        long result = db.insert(TABLE_NAME, null, cv);
        return result != -1;
    }

    public boolean updateUser(String id, String nom, String prenom, String mail, String mdp,
                              String datenaiss, String taille, String poids) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL2, nom);
        cv.put(COL3, prenom);
        cv.put(COL4, mail);
        cv.put(COL5, datenaiss);
        cv.put(COL6, mdp);
        cv.put(COL7, taille);
        cv.put(COL8, poids);

        int result = db.update(TABLE_NAME, cv, "ID = ?", new String[]{id});
        return result > 0;
    }

    public int deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{String.valueOf(id)});
    }

    public Cursor getUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ID = ?", new String[]{String.valueOf(id)});
    }

    // -----------------------------
    // 🔥 LOGIN VERIFICATION METHOD
    // -----------------------------
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME + " WHERE MAIL = ? AND MDP = ?",
                new String[]{email, password}
        );

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}
