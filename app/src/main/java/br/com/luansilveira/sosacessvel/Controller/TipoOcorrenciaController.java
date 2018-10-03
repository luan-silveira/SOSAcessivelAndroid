package br.com.luansilveira.sosacessvel.Controller;

import android.content.Context;

import com.j256.ormlite.dao.BaseDaoImpl;

import java.sql.SQLException;
import java.util.List;

import br.com.luansilveira.sosacessvel.Model.TipoOcorrencia;
import br.com.luansilveira.sosacessvel.utils.DB;

public class TipoOcorrenciaController extends BaseDaoImpl<TipoOcorrencia, Integer> {

    private DB db;

    public TipoOcorrenciaController(Context context) throws SQLException {
        super(TipoOcorrencia.class);
        db = new DB(context);
        setConnectionSource(db.getConnectionSource());
        initialize();
    }

    public List<TipoOcorrencia> getTiposPelaClassificacao(Integer idClassificacao) throws SQLException {
        return this.queryForEq("id_classificacao_ocorrencia", idClassificacao);
    }
}
