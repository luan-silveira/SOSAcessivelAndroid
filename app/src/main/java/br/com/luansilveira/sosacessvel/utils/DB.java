package br.com.luansilveira.sosacessvel.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import br.com.luansilveira.sosacessvel.R;

public class DB extends SQLiteOpenHelper{

    private static final String DB_NOME = "sos_acessivel";
    private static final int DB_VERSAO = 1;
    private Context context;

    public DB(Context context) {
        super(context, DB_NOME, null, DB_VERSAO);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS usuario(" +
                "_id integer primary key autoincrement, " +
                "nome text not null, " +
                "data_nascimento text not null, " +
                "tipo_sanguineo text not null, " +
                "fator_rh_sanguineo text not null, " +
                "endereco text, " +
                "informacoes_medicas text)");

        db.execSQL("CREATE TABLE IF NOT EXISTS classificacao_ocorrencia(" +
                "_id integer primary key autoincrement, " +
                "descricao text not null)");

        db.execSQL("CREATE TABLE IF NOT EXISTS tipo_ocorrencia(" +
                "_id integer primary key autoincrement," +
                "id_classificacao_ocorrencia integer not null, " +
                "descricao text not null)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int execSQLFromFile(int resourceId) throws IOException {
        int result = 0;
        StringBuilder SQL = new StringBuilder();

        InputStream insertsStream = context.getResources().openRawResource(resourceId);
        BufferedReader insertReader = new BufferedReader(new InputStreamReader(insertsStream));

        while (insertReader.ready()) {
            String linha = insertReader.readLine();
            SQL.append(linha + " ");
            if(linha.indexOf(';') > -1){
                getWritableDatabase().execSQL(SQL.toString());
                SQL.setLength(0);
                result++;
            }
        }
        insertReader.close();

        return result;
    }
}
