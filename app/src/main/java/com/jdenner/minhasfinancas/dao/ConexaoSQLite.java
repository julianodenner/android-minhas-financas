package com.jdenner.minhasfinancas.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Juliano on 04/08/2016.
 */
public class ConexaoSQLite extends SQLiteOpenHelper {

    private static final String DB_NAME = "minhasfinancas.db";
    private static final int DB_VERSION = 1;

    public ConexaoSQLite(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("" +
                "CREATE TABLE tb_movimento (" +
                "   codigo INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "   descricao VARCHAR(250) NOT NULL," +
                "   valor DECIMAL(12,2) NOT NULL," +
                "   data TEXT NOT NULL," +
                "   tipo INTEGER NOT NULL," +
                "   grupo INTEGER NOT NULL" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
