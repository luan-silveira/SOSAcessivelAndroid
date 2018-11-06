package br.com.luansilveira.sosacessvel.Controller;

import android.content.Context;

import com.j256.ormlite.dao.BaseDaoImpl;

import java.sql.SQLException;

import br.com.luansilveira.sosacessvel.Model.InstituicaoAtendimento;
import br.com.luansilveira.sosacessvel.utils.DB;

public class InstituicaoController extends BaseDaoImpl<InstituicaoAtendimento, Integer> {

    private DB db;

    public InstituicaoController(Context context) throws SQLException {
        super(InstituicaoAtendimento.class);
        db = new DB(context);
        setConnectionSource(db.getConnectionSource());
        initialize();
    }

}
