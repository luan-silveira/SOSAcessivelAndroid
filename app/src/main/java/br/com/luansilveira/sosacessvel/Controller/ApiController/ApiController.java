package br.com.luansilveira.sosacessvel.Controller.ApiController;

import android.content.Context;

abstract class ApiController {

    final String APP_URL = "http://10.0.0.84/api/";
    Context context;

    public ApiController(Context context) {
        this.context = context;
    }
}
