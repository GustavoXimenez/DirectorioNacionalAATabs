package com.programandounmundomejor.directorionacionalaa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.programandounmundomejor.directorionacionalaa.Adapters.GruposXAreaAdapter;

import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstGruposXArea;

public class GruposXAreaActivity extends AppCompatActivity {

    private GruposXAreaAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupos_xarea);

        if(adapter == null){
            adapter = new GruposXAreaAdapter(this, lstGruposXArea);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_gruposXArea);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
