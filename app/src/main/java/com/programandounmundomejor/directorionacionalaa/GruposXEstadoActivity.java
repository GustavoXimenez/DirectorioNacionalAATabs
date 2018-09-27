package com.programandounmundomejor.directorionacionalaa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.programandounmundomejor.directorionacionalaa.Adapters.GruposXCPAdapter;
import com.programandounmundomejor.directorionacionalaa.Adapters.GruposXEstadoAdapter;

import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstGruposXCP;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstGruposXEstado;

public class GruposXEstadoActivity extends AppCompatActivity {

    private GruposXEstadoAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupos_xestado);

        if(adapter == null){
            adapter = new GruposXEstadoAdapter(this, lstGruposXEstado);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_gruposXEstado);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
