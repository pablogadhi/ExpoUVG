package com.uvg.expo.Networking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.uvg.expo.R;


/**
 * Created by josePablo on 27/04/2017.
 */

public class NetworkingFragment extends android.support.v4.app.Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstantState){
        View inflado =  inflater.inflate(R.layout.lista_escoger, container, false);

        String [] itemsLista = {"Stands","Compartir en Facebook","Experiencia Twitter"};
        ListView listaNet = (ListView) inflado.findViewById(R.id.ListNet);

        ArrayAdapter<String> adpLista = new ArrayAdapter<String>(
                getActivity(),android.R.layout.simple_list_item_1,itemsLista
        );

        listaNet.setAdapter(adpLista);

        listaNet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    tab1 tabulacion= new tab1();
                    ft.replace(R.id.fragmentContainer, tabulacion);
                    ft.commit();

                }else if (position==1){
                    Intent intent = new Intent(getActivity(), FacebookActivity.class);
                    startActivity(intent);

                }
                /**
                 * Aqui esta para llamar a la main activity del Twitter (snow) :)
                else if (position ==2){
                    Intent intent = new Intent(getActivity(), MainTabbedActivity.class);
                    startActivity(intent);

                }
                 */

            }
        });
        return inflado;
    }
}
