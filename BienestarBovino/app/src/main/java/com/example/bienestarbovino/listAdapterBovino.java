package com.example.bienestarbovino;

import android.content.Context;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;

import java.util.List;

import model.listaInventariobovinos;

public class listAdapterBovino extends ArrayAdapter<listaInventariobovinos> {

    private List<listaInventariobovinos> inventario;
    private Context context;
    private int resourceLayout;

    public listAdapterBovino(@NonNull Context context, int resource, @NonNull List<listaInventariobovinos> objects){
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
