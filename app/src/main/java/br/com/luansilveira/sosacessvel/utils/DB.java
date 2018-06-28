package br.com.luansilveira.sosacessvel.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper{

    private static final String DB_NOME = "sos_acessivel";
    private static final int DB_VERSAO = 1;

    public DB(Context context) {
        super(context, DB_NOME, null, DB_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuario(" +
                "_id integer primary key autoincrement," +
                "nome text not null," +
                "data_nascimento text not null," +
                "tipo_sanguineo text not null," +
                "fator_rh_sanguineo text not null," +
                "endereco text," +
                "informacoes_medicas text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
