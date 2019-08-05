package br.com.luansilveira.sosacessvel.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class Permissoes {

    public static void solicitarPermissoes(Activity context) {
        ArrayList<String> permissoesRequisitar = new ArrayList<>();
        String[] permissoesRequisitarArray = new String[]{};

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            permissoesRequisitar.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            permissoesRequisitar.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (!permissoesRequisitar.isEmpty()) {
            ActivityCompat.requestPermissions(context,
                    permissoesRequisitar.toArray(permissoesRequisitarArray), 1);
        }
    }

    public static boolean isPermissaoLocalizacao(Context context) {
        return ((ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED));
    }

}
