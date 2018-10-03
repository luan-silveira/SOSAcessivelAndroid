package br.com.luansilveira.sosacessvel.Controller;

import android.content.Context;

import com.j256.ormlite.dao.BaseDaoImpl;

import java.sql.SQLException;

import br.com.luansilveira.sosacessvel.Model.Usuario;
import br.com.luansilveira.sosacessvel.utils.DB;

public class UsuarioController extends BaseDaoImpl<Usuario, Integer> {
    private DB db;

    public UsuarioController(Context context) throws SQLException {
        super(Usuario.class);
        db = new DB(context);
        setConnectionSource(db.getConnectionSource());
        initialize();
    }

    public Usuario getUsuario() throws SQLException {
        return this.queryForAll().get(0);
    }

    public boolean existeUsuario() throws SQLException {
        return  (this.queryForAll().size() > 0);
    }
}
