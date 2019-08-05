package br.com.luansilveira.sosacessvel.utils;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Geolocalizacao {

    private Context context;
    private Location local;
    private GeolocalizacaoListener listener;

    public Geolocalizacao(Context context) {
        this.context = context;
    }

    public void getLocalizacao() {
        FusedLocationProviderClient fusedLocation = LocationServices.getFusedLocationProviderClient(context);

        try {
            final Task<Location> localizacao = fusedLocation.getLastLocation();
            localizacao.addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful()) {
                        local = task.getResult();
                        if (local != null) {
                            listener.onLocalizacaoEncontrada(local);
                        } else {
                            listener.onFalhaEncontrarLocalizacao();
                        }
                    }
                }
            });

        } catch (SecurityException e) {
            Log.e("Exception", e.getMessage());
        }
    }

    public Geolocalizacao setListener(GeolocalizacaoListener listener) {
        this.listener = listener;

        return this;
    }

    public interface GeolocalizacaoListener {
        void onLocalizacaoEncontrada(Location local);

        void onFalhaEncontrarLocalizacao();
    }
}
