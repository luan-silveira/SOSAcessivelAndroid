package br.com.luansilveira.sosacessvel.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.luansilveira.sosacessvel.Model.TipoOcorrencia;
import br.com.luansilveira.sosacessvel.utils.DB;

public class TipoOcorrenciaController {
    private DB db;
    private SQLiteDatabase sqlDb;
    private Context context;

    public TipoOcorrenciaController(Context context) {
        this.context = context;
        db = new DB(context);
    }

    public long create(final TipoOcorrencia classificacao) {
        ContentValues dados = new ContentValues();
        long resultado;

        sqlDb = db.getWritableDatabase();
        if(classificacao.getId() != null){
            dados.put("_id", classificacao.getId());
        }
        dados.put("descricao", classificacao.getDescricao());

        resultado = sqlDb.insert("tipo_ocorrencia", null, dados);
        sqlDb.close();
        return resultado;
    }

    public long update(final TipoOcorrencia classificacao) {

        ContentValues dados = new ContentValues();
        long resultado;

        sqlDb = db.getWritableDatabase();
        if(classificacao.getId() != null){
            dados.put("_id", classificacao.getId());
        }
        dados.put("descricao", classificacao.getDescricao());

        String where = "_id = " + classificacao.getId();

        resultado = sqlDb.update("tipo_ocorrencia", dados, where, null);
        sqlDb.close();

        return resultado;
    }

    public long delete(final TipoOcorrencia classificacao) {

        String where = "_id = " + classificacao.getId();
        sqlDb = db.getReadableDatabase();

        long resultado = sqlDb.delete("tipo_ocorrencia", where, null);
        return resultado;

    }

    public Cursor retrieve() {
        sqlDb = db.getReadableDatabase();
        String[] campos = {"_id", "descricao", "id_classificacao_ocorrencia"};

        Cursor cursor = sqlDb.query("tipo_ocorrencia", campos,
                null, null,null,null,null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        sqlDb.close();
        return cursor;
    }

    public Cursor retrieve(Integer idClassificacao) {
        sqlDb = db.getReadableDatabase();
        String where = "id_classificacao_ocorrencia = " + idClassificacao;
        String[] campos = {"_id", "descricao", "id_classificacao_ocorrencia"};

        Cursor cursor = sqlDb.query("tipo_ocorrencia", campos,
                where, null,null,null,null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        sqlDb.close();
        return cursor;
    }

    public TipoOcorrencia getTipoOcorrencia(Integer id){
        sqlDb = db.getReadableDatabase();
        String[] campos = {"_id", "descricao", "id_classificacao_ocorrencia"};
        String where = "_id = " + id;

        Cursor cursor = sqlDb.query("tipo_ocorrencia", campos,
                where, null,null,null,null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        sqlDb.close();

        return this.popularTipoOcorrencia(cursor);
    }

    public TipoOcorrencia popularTipoOcorrencia(Cursor cursor){
        TipoOcorrencia tipo = new TipoOcorrencia();
        tipo.setId(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
        tipo.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow("descricao")));
        tipo.setClassificacaoOcorrencia(
                (new ClassificacaoOcorrenciaController(context)).getClassificacaoOcorrencia(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id_classificacao_ocorrencia")))
        );

        return tipo;
    }
}
