package com.example.bienestarbovino;

import android.content.Context;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;

import java.util.List;

import model.listaInventarioPersonal;

public class listAdapterPersonal extends ArrayAdapter<listaInventarioPersonal> {

    private List<listaInventarioPersonal> inventario;
    private Context context;
    private int resourceLayout;

    public listAdapterPersonal(@NonNull Context context, int resource, @NonNull List<listaInventarioPersonal> objects){
        super(context, resource, objects);
        this.inventario = objects;
        this.context = context;
        this.resourceLayout = resource;
    }


}
