package com.example.bienestarbovino;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import model.listaInventario;

public class listAdapter  extends ArrayAdapter<listaInventario> {

    private List<listaInventario> inventario;
    private Context context;
    private int resourceLayout;

    public listAdapter(@NonNull Context context, int resource, @NonNull List<listaInventario> objects){
        super(context, resource, objects);
        this.inventario = objects;
        this.context = context;
        this.resourceLayout = resource;
    }

//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        View view = convertView;
//
//        if(view == null){
//            view = LayoutInflater.from(context).inflate()
//        }
//        return view;
//    }
}
