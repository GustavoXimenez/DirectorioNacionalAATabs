package com.programandounmundomejor.directorionacionalaa.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.programandounmundomejor.directorionacionalaa.Clases.Callback;
import com.programandounmundomejor.directorionacionalaa.Clases.PostRequest;
import com.programandounmundomejor.directorionacionalaa.R;

import java.util.HashMap;
import java.util.Map;

import static com.programandounmundomejor.directorionacionalaa.Clases.Global.areaSelect;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.coloniaSelect;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.distritosSelect;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.estadoSelect;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstAreas;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstAreasComplete;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstColonias;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstDistritos;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstEstados;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstEstadosComplete;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstMunicipios;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.municipioSelect;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.signature;


public class AreaFragment extends Fragment {

    private EditText edtArea, edtDistrito;
    private View view;

    //Variables globales
    private PostRequest postRequest = new PostRequest();
    private Callback callback = new Callback();
    private int selectedItem = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_area, container, false);
        initViews();
        return view;
    }

    private void initViews(){
        edtArea = (EditText) view.findViewById(R.id.edtArea);
        edtDistrito = (EditText) view.findViewById(R.id.edtDistrito);

        initActions();
    }

    private void initActions(){
        edtArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectArea();
            }
        });

        edtDistrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDistrito();
            }
        });
    }

    private void selectArea(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                final String response = postRequest.enviarPost("signature="+signature, "searchAreas.php");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.processingResult("areas", response);
                        if(lstAreas.size() > 0){
                            //Creamos array de estados
                            String[] areasArr = new String[lstAreas.size()];
                            areasArr = lstAreas.toArray(areasArr);
                            alertDialog(areasArr, edtArea, "area");
                        } else {
                            Toast.makeText(getActivity(), "error en el servicio", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
        thread.start();
    }

    private void selectDistrito(){
        if(areaSelect != null){
            //Buscamos el idEstado
            final int idArea = searchId(lstAreasComplete, areaSelect);
            if(idArea > 0){
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        final String response = postRequest.enviarPost("signature="+signature+"&idArea="+idArea, "searchDistritos.php");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback.processingResult("distritos", response);
                                if(lstDistritos.size() > 0){
                                    //Creamos array de municipios
                                    String[] distritosArr = new String[lstDistritos.size()];
                                    distritosArr = lstDistritos.toArray(distritosArr);
                                    alertDialog(distritosArr, edtDistrito, "distrito");
                                } else {
                                    Toast.makeText(getActivity(), "error en el servicio", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                };
                thread.start();
            } else {
                Toast.makeText(getActivity(), "error de distrito", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Selecciona una area", Toast.LENGTH_SHORT).show();
        }
    }

    private int searchId(HashMap<Integer, String> list, String item){
        int id = 0;
        for(Map.Entry<Integer, String> entry : list.entrySet()){
            String estado = entry.getValue();
            if(estado.equals(item)) id = entry.getKey();
        }
        return id;
    }

    private void alertDialog(final String[] array, final EditText editText, final String type){
        //Initialize a new AlertDialog Builder
        final AlertDialog.Builder cultureDialog = new AlertDialog.Builder(getActivity());

        cultureDialog.setSingleChoiceItems(array, selectedItem, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                selectedItem = which;
            }
        });

        //Define the AlertDialog positive/ok/yes button
        cultureDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                editText.setText(array[selectedItem]);
                switch (type){
                    case "area":
                        areaSelect = array[selectedItem];
                        distritosSelect = null;
                        edtDistrito.setText("");
                        break;
                    case "distrito":
                        distritosSelect = array[selectedItem];
                        break;
                }

            }
        });

        //Display the Alert Dialog on app interface
        AlertDialog alertDialog = cultureDialog.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
}
