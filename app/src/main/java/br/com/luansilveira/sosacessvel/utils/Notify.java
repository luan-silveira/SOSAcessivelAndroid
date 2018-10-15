package br.com.luansilveira.sosacessvel.utils;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class Notify {

    public static void criarNotificacao(Context context, Intent contentIntent,  int icon, CharSequence title, CharSequence text){
        int id = 1;

        PendingIntent p = getPendingIntent(id, contentIntent, context);

        NotificationCompat.Builder notificacao = new NotificationCompat.Builder(context);
        notificacao.setSmallIcon(icon);
        notificacao.setContentTitle(title);
        notificacao.setContentText(text);
        notificacao.setContentIntent(p);

        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        nm.notify(id, notificacao.build());
    }

    private static PendingIntent getPendingIntent(int id, Intent intent, Context context){
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(intent.getComponent());
        stackBuilder.addNextIntent(intent);

        PendingIntent p = stackBuilder.getPendingIntent(id, PendingIntent.FLAG_UPDATE_CURRENT);
        return p;
    }

}
