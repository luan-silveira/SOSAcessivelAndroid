package br.com.luansilveira.sosacessvel.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.luansilveira.sosacessvel.Model.Usuario;
import br.com.luansilveira.sosacessvel.utils.DB;

public class UsuarioController {

    private DB db;
    private SQLiteDatabase sqlDb;

    public UsuarioController(Context context) {
        db = new DB(context);
    }

    public long create(final Usuario usuario) {
        ContentValues dados = new ContentValues();
        long resultado;

        sqlDb = db.getWritableDatabase();
        dados.put("nome", usuario.getNome());
        dados.put("data_nascimento", usuario.getDataNascimento().toString());
        dados.put("tipo_sanguineo", usuario.getTipoSanguineo().toString());
        dados.put("fator_rh_sanguineo", usuario.getRhSanguineo().toString());
        dados.put("endereco", usuario.getEndereco());
        dados.put("informacoes_medicas", usuario.getInfMedicas());

        resultado = sqlDb.insert("usuarios", null, dados);
        sqlDb.close();
        return resultado;
    }

    public long update(final Usuario usuario) {

        ContentValues dados = new ContentValues();
        long resultado;

        sqlDb = db.getWritableDatabase();
        dados.put("nome", usuario.getNome());
        dados.put("data_nascimento", usuario.getDataNascimento().toString());
        dados.put("tipo_sanguineo", usuario.getTipoSanguineo().toString());
        dados.put("fator_rh_sanguineo", usuario.getRhSanguineo().toString());
        dados.put("endereco", usuario.getEndereco());
        dados.put("informacoes_medicas", usuario.getInfMedicas());

        String where = "_id = " + usuario.getId();

        resultado = sqlDb.update("Usuarios", dados, where, null);
        sqlDb.close();

        return resultado;
    }

    public Usuario getById(int id) {

        String[] campos = {"_id", "nome", "data_nascimento", "tipo_sanguineo", "fator_rh_sanguineo", "endereco", "informacoes_medicas"};
        String where = "_id = " + id;
        sqlDb = db.getReadableDatabase();

        Cursor cursor = sqlDb.query("Usuarios", campos,
                null, null,null,null,null);

        if (cursor == null) {
            return null;
        }

        cursor.moveToFirst();
        sqlDb.close();

        Usuario usuario = new Usuario();
        usuario.setId(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
        usuario.setNome(cursor.getString(cursor.getColumnIndexOrThrow("descricao")));
        usuario.setDataNascimento(cursor.getString(cursor.getColumnIndexOrThrow("data_nascimento")));
        usuario.setTipoSanguineo(cursor.getString(cursor.getColumnIndexOrThrow("tipo_sanguineo")));
        usuario.setRhSanguineo(cursor.getString(cursor.getColumnIndexOrThrow("fator_rh_sanguineo")));
        usuario.setEndereco(cursor.getString(cursor.getColumnIndexOrThrow("endereco")));
        usuario.setInfMedicas(cursor.getString(cursor.getColumnIndexOrThrow("informacoes_medicas")));

        return usuario;
    }
}
