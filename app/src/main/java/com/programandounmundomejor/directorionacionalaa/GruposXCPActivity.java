package com.programandounmundomejor.directorionacionalaa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.programandounmundomejor.directorionacionalaa.Adapters.GruposXCPAdapter;

import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstGruposXCP;

public class GruposXCPActivity extends AppCompatActivity {

    private GruposXCPAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupos_xcp);

        if(adapter == null){
            adapter = new GruposXCPAdapter(this,this, lstGruposXCP);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_gruposXCP);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
