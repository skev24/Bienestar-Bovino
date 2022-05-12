package com.example.bienestarbovino;

import android.content.Context;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;

import java.util.List;

import model.lista_Inventario;

public class listAdapter  extends ArrayAdapter<lista_Inventario> {

    private List<lista_Inventario> inventario;
    private Context context;
    private int resourceLayout;

    public listAdapter(@NonNull Context context, int resource, @NonNull List<lista_Inventario> objects){
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
