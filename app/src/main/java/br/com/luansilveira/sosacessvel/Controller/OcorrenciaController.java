package br.com.luansilveira.sosacessvel.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.luansilveira.sosacessvel.Model.Ocorrencia;
import br.com.luansilveira.sosacessvel.utils.DB;

public class OcorrenciaController {

    private DB db;
    private SQLiteDatabase sqlDb;
    private Context context;

    public OcorrenciaController(Context context){
        this.db = new DB(context);
        this.context = context;
    }


    public long create(final Ocorrencia ocorrencia) {

        long resultado;
        sqlDb = db.getWritableDatabase();

        resultado = sqlDb.insert("ocorrencia", null, this.popularDados(ocorrencia));

        return resultado;
    }

    public long update(final Ocorrencia ocorrencia) {
        long resultado;

        sqlDb = db.getWritableDatabase();

        String where = "_id = " + ocorrencia.getId();
        resultado = sqlDb.update("ocorrencia", this.popularDados(ocorrencia), where, null);

        return resultado;
    }

    public long delete(final Ocorrencia ocorrencia) {
        String where = "_id = " + ocorrencia.getId();
        sqlDb = db.getReadableDatabase();

        long resultado = sqlDb.delete("ocorrencia", where, null);
        return resultado;

    }

    private Cursor retrieveDB(String where) {

        sqlDb = db.getReadableDatabase();
        String[] campos = {"_id", "_key", "id_tipo_ocorrencia", "descricao", "localizacao", "latitude", "longitude",
                "data_ocorrencia", "status", "id_atendente", "mensagem_atendente"};

        Cursor cursor = sqlDb.query("ocorrencia", campos,
                where, null,null,null,null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        sqlDb.close();

        return cursor;
    }

    public Cursor retrieve() {
        return this.retrieveDB(null);
    }

    public Cursor retrieve(Integer id){
        String where = "_id = " + id;
        return this.retrieveDB(where);
    }

    public Ocorrencia getOcorrencia(Integer id){
        return this.popularOcorrencia(this.retrieve(id));
    }

    public ArrayList<Ocorrencia> getListaOcorrencias(){
        ArrayList<Ocorrencia> lista = new ArrayList<>();
        Cursor cursor = this.retrieve();

        if (cursor != null && cursor.getCount() > 0)
            do {
                Ocorrencia ocorrencia = this.popularOcorrencia(cursor);
                lista.add(ocorrencia);
            } while (cursor.moveToNext());

        return lista;
    }

    public ArrayList<Ocorrencia> getListaOcorrencias(int status){
        ArrayList<Ocorrencia> lista = new ArrayList<>();
        Cursor cursor = this.retrieveDB("status = " + status);

        if (cursor != null && cursor.getCount() > 0)
            do {
                Ocorrencia ocorrencia = this.popularOcorrencia(cursor);
                lista.add(ocorrencia);
            } while (cursor.moveToNext());

        return lista;
    }

//    public long cadastrarOcorrencia(Ocorrencia ocorrencia){
//
//    }

    private ContentValues popularDados(Ocorrencia ocorrencia){
        return popularDados(ocorrencia, false);
    }

    private ContentValues popularDados(Ocorrencia ocorrencia, boolean cadastrarOcorrencia){
        ContentValues dados = new ContentValues();

        if(ocorrencia.getId() != null) dados.put("_id", ocorrencia.getId());
        if(ocorrencia.getKey() != null) dados.put("_key", ocorrencia.getKey());

        dados.put("id_tipo_ocorrencia", ocorrencia.getTipoOcorrencia().getId());
        dados.put("descricao", ocorrencia.getDescricao());
        dados.put("localizacao", ocorrencia.getLocalizacao());
        if (!cadastrarOcorrencia) {
            dados.put("latitude", ocorrencia.getLatitude());
            dados.put("longitude", ocorrencia.getLongitude());
            dados.put("data_ocorrencia", ocorrencia.getDataOcorrencia());
            dados.put("status", ocorrencia.getStatus());
            if(ocorrencia.getAtendente() != null){
                dados.put("id_atendente", ocorrencia.getAtendente().getId());
                dados.put("mensagem_atendente", ocorrencia.getMensagemAtendente());
            }
        }

        return dados;
    }

    private Ocorrencia popularOcorrencia(Cursor cursor){
        Integer idAtendente = cursor.getInt(cursor.getColumnIndexOrThrow("id_atendente"));

        Ocorrencia ocorrencia = new Ocorrencia(
                (new UsuarioController(context)).getUsuario(),
                (new TipoOcorrenciaController(context)).getTipoOcorrencia(cursor.getInt(cursor.getColumnIndexOrThrow("id_tipo_ocorrencia"))),
                cursor.getString(cursor.getColumnIndexOrThrow("descricao")),
                cursor.getString(cursor.getColumnIndexOrThrow("localizacao")),
                cursor.getDouble(cursor.getColumnIndexOrThrow("latitude")),
                cursor.getDouble(cursor.getColumnIndexOrThrow("longitude"))
        );

        if(idAtendente != null && idAtendente != 0){
            ocorrencia.setAtendente((new AtendenteController(context)).getAtendente(idAtendente));
            ocorrencia.setMensagemAtendente(cursor.getString(cursor.getColumnIndexOrThrow("mensagem_atendente")));
        }

        ocorrencia.setKey(cursor.getString(cursor.getColumnIndexOrThrow("_key")));

        return ocorrencia;
    }
}
