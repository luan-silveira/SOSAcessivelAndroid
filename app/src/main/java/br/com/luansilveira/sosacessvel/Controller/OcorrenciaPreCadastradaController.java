package br.com.luansilveira.sosacessvel.Controller;

import android.content.Context;

import com.j256.ormlite.dao.BaseDaoImpl;

import java.sql.SQLException;
import java.util.List;

import br.com.luansilveira.sosacessvel.Model.OcorrenciaPreCadastrada;
import br.com.luansilveira.sosacessvel.utils.DB;

public class OcorrenciaPreCadastradaController extends BaseDaoImpl<OcorrenciaPreCadastrada, Integer> {

    private DB db;

    public OcorrenciaPreCadastradaController(Context context) throws SQLException {
        super(OcorrenciaPreCadastrada.class);
        db = new DB(context);
        setConnectionSource(db.getConnectionSource());
        initialize();
    }

    public List<OcorrenciaPreCadastrada> getListaOcorrencias() throws SQLException {
        return this.queryForAll();
    }

}
