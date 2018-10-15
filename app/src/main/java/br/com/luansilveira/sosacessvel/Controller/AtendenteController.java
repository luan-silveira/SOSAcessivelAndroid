package br.com.luansilveira.sosacessvel.Controller;

import android.content.Context;

import com.j256.ormlite.dao.BaseDaoImpl;

import java.sql.SQLException;

import br.com.luansilveira.sosacessvel.Model.Atendente;
import br.com.luansilveira.sosacessvel.utils.DB;

public class AtendenteController extends BaseDaoImpl<Atendente, Integer> {

    private DB db;

    public AtendenteController(Context context) throws SQLException {
        super(Atendente.class);
        db = new DB(context);
        setConnectionSource(db.getConnectionSource());
        initialize();
    }

}
