package com.jose.crudandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CriaBanco extends SQLiteOpenHelper {

    private String scriptSqlCreate = "CREATE TABLE USER(ID INTEGER PRIMARY KEY AUTOINCREMENT, NOME TEXT NOT NULL," +
                " CPF TEXT NOT NULL, TELEFONE TEXT NOT NULL, EMAIL TEXT NOT NULL)";
    private String scriptSqlDelete = "DROP TABLE IF EXISTS USER";

    public CriaBanco(Context context) {
        super(context, "crudAndroid", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(scriptSqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(scriptSqlDelete);
        onCreate(db);
    }
}
