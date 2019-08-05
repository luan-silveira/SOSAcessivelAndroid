package br.com.luansilveira.sosacessvel.Controller;

import android.database.Cursor;

public interface ControllerInterface<T> {
    long create(final T obj);

    long update(final T obj);

    long delete(final T obj);

    Cursor retrieve();
}
