package br.com.luansilveira.sosacessvel.Controller;

import android.content.Context;

import com.j256.ormlite.dao.BaseDaoImpl;

import java.sql.SQLException;

import br.com.luansilveira.sosacessvel.Model.ClassificacaoOcorrencia;
import br.com.luansilveira.sosacessvel.utils.DB;

public class ClassificacaoOcorrenciaController extends BaseDaoImpl<ClassificacaoOcorrencia, Integer> {

    private DB db;

    public ClassificacaoOcorrenciaController(Context context) throws SQLException {
        super(ClassificacaoOcorrencia.class);
        db = new DB(context);
        setConnectionSource(db.getConnectionSource());
        initialize();
    }

}
