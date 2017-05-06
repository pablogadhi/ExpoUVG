package com.uvg.expo.Networking;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.uvg.expo.R;


/**
 * Created by josePablo on 20/04/2017.
 */

public class tab1 extends android.support.v4.app.Fragment {

    ListView lista ;
    String [] titulos;
    String [] descripciones;
    int[] images={R.drawable.game,R.drawable.news,R.drawable.encuestas};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_1,container,false);

        Resources res = getResources();
        titulos = res.getStringArray(R.array.titulos);
        descripciones = res.getStringArray(R.array.descrip);
        lista = (ListView) v.findViewById(R.id.lista);
        ListaVista listaVista = new ListaVista(getActivity(),titulos,images,descripciones);
        lista.setAdapter(listaVista);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int pos, long id) {
                if(pos==0){
                    Intent intent = new Intent(getActivity(), rBar.class);
                    startActivity(intent);
                }
            }
        });

        return v;
    }


}


class ListaVista extends ArrayAdapter<String> {
    Context context;
    int[] images;
    String[] titArray;
    String [] desArray;
    ListaVista(Context cont,String[] titulos, int imgs[], String[] desc){
        super(cont,R.layout.prelista,R.id.titulo,titulos);
        this.context = cont;
        this.images = imgs;
        this.titArray = titulos;
        this.desArray = desc;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.prelista,parent,false);
        ImageView imagen = (ImageView) v.findViewById(R.id.ImagenListView);
        TextView tit = (TextView) v.findViewById(R.id.titulo);
        TextView des = (TextView) v.findViewById(R.id.descripcion);

        imagen.setImageResource(images[position]);
        tit.setText(titArray[position]);
        des.setText(desArray[position]);

        return v;
    }



}



