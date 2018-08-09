package br.com.luansilveira.sosacessvel.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.luansilveira.sosacessvel.Model.ClassificacaoOcorrencia;
import br.com.luansilveira.sosacessvel.utils.DB;

public class ClassificacaoOcorrenciaController {

    private DB db;
    private SQLiteDatabase sqlDb;

    public ClassificacaoOcorrenciaController(Context context) {
        db = new DB(context);
    }

    public long create(final ClassificacaoOcorrencia classificacao) {
        ContentValues dados = new ContentValues();
        long resultado;

        sqlDb = db.getWritableDatabase();
        if(classificacao.getId() != null){
            dados.put("_id", classificacao.getId());
        }
        dados.put("descricao", classificacao.getDescricao());

        resultado = sqlDb.insert("classificacao_ocorrencia", null, dados);
        sqlDb.close();
        return resultado;
    }

    public long update(final ClassificacaoOcorrencia classificacao) {

        ContentValues dados = new ContentValues();
        long resultado;

        sqlDb = db.getWritableDatabase();
        if(classificacao.getId() != null){
            dados.put("_id", classificacao.getId());
        }
        dados.put("descricao", classificacao.getDescricao());

        String where = "_id = " + classificacao.getId();

        resultado = sqlDb.update("classificacao_ocorrencia", dados, where, null);
        sqlDb.close();

        return resultado;
    }

    public long delete(final ClassificacaoOcorrencia classificacao) {

        String where = "_id = " + classificacao.getId();
        sqlDb = db.getReadableDatabase();

        long resultado = sqlDb.delete("classificacao_ocorrencia", where, null);
        return resultado;

    }

    public Cursor retrieve() {
        sqlDb = db.getReadableDatabase();
        String[] campos = {"_id", "descricao"};

        Cursor cursor = sqlDb.query("classificacao_ocorrencia", campos,
                null, null,null,null,null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        sqlDb.close();
        return cursor;
    }

    public ClassificacaoOcorrencia getClassificacaoOcorrencia(Integer id){
        sqlDb = db.getReadableDatabase();
        String[] campos = {"_id", "descricao"};
        String where = "_id = " + id;

        Cursor cursor = sqlDb.query("classificacao_ocorrencia", campos,
                where, null,null,null,null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        sqlDb.close();

        return new ClassificacaoOcorrencia(cursor.getInt(cursor.getColumnIndexOrThrow("_id")),
                cursor.getString(cursor.getColumnIndexOrThrow("descricao")));
    }
}
