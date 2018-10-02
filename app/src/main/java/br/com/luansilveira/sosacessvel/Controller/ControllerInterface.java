package br.com.luansilveira.sosacessvel.Controller;

import android.database.Cursor;

public interface ControllerInterface<T> {
    public long create(final T obj);
    public long update(final T obj);
    public long delete(final T obj);
    public Cursor retrieve();
}
