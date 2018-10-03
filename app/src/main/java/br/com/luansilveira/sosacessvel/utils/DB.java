package br.com.luansilveira.sosacessvel.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

import br.com.luansilveira.sosacessvel.Model.Atendente;
import br.com.luansilveira.sosacessvel.Model.ClassificacaoOcorrencia;
import br.com.luansilveira.sosacessvel.Model.Ocorrencia;
import br.com.luansilveira.sosacessvel.Model.TipoOcorrencia;
import br.com.luansilveira.sosacessvel.Model.Usuario;

public class DB extends OrmLiteSqliteOpenHelper{

    private static final String DB_NOME = "sos_acessivel";
    private static final int DB_VERSAO = 1;
    private Context context;

    public DB(Context context) {
        super(context, DB_NOME, null, DB_VERSAO);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, ClassificacaoOcorrencia.class);
            TableUtils.createTableIfNotExists(connectionSource, TipoOcorrencia.class);
            TableUtils.createTableIfNotExists(connectionSource, Usuario.class);
            TableUtils.createTableIfNotExists(connectionSource, Atendente.class);
            TableUtils.createTableIfNotExists(connectionSource, Ocorrencia.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

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
