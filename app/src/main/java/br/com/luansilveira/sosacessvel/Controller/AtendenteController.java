package br.com.luansilveira.sosacessvel.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.luansilveira.sosacessvel.Model.Atendente;

import br.com.luansilveira.sosacessvel.utils.DB;

public class AtendenteController{

    private final String TABELA = "atendente";

    private DB db;
    private SQLiteDatabase sqlDb;

    public AtendenteController(Context context) {
        this.db = new DB(context);
    }

    
    public long create(Atendente atendente) {
        sqlDb = db.getWritableDatabase();
        long resultado = sqlDb.insert(TABELA, null, this.popularDados(atendente));

        return resultado;
    }

    
    public long update(Atendente atendente) {
        String where = "_id = " + atendente.getId();
        sqlDb = db.getWritableDatabase();
        long resultado = sqlDb.update(TABELA, this.popularDados(atendente), where, null);

        return resultado;
    }

    
    public long delete(Atendente atendente) {
        String where = "_id = " + atendente.getId();
        sqlDb = db.getWritableDatabase();
        long resultado = sqlDb.delete(TABELA, where, null);

        return resultado;
    }


    private Cursor retrieveDB(String where){
        sqlDb = db.getReadableDatabase();
        String[] campos = {"_id", "nome", "id_instituicao"};

        Cursor cursor = sqlDb.query(TABELA, campos, where, null, null, null, null);

        if(cursor != null) cursor.moveToFirst();

        return cursor;
    }

    public Cursor retrieve() {
        return this.retrieveDB(null);
    }

    public Cursor retrieve(Integer id){
        return this.retrieveDB("_id = " + id);
    }

    public Atendente getAtendente(Integer id){
        return this.popularAtendente(this.retrieve(id));
    }
    
    private ContentValues popularDados(Atendente atendente){
        ContentValues dados = new ContentValues();

        if(atendente.getId() != null){
            dados.put("_id", atendente.getId());
        }

        dados.put("nome", atendente.getNome());
        dados.put("instituicao_atendimento", atendente.getInstituicao());

        return dados;
    }

    private Atendente popularAtendente(Cursor cursor){
        return new Atendente(
                cursor.getInt(cursor.getColumnIndexOrThrow("_id")),
                cursor.getString(cursor.getColumnIndexOrThrow("nome")),
                cursor.getString(cursor.getColumnIndexOrThrow("instituicao_atendimento"))
                );
    }
}
