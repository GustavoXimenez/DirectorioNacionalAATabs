package com.programandounmundomejor.directorionacionalaa.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.programandounmundomejor.directorionacionalaa.Clases.Callback;
import com.programandounmundomejor.directorionacionalaa.Clases.GlobalMethos;
import com.programandounmundomejor.directorionacionalaa.Clases.PostRequest;
import com.programandounmundomejor.directorionacionalaa.GruposXCPActivity;
import com.programandounmundomejor.directorionacionalaa.GruposXEstadoActivity;
import com.programandounmundomejor.directorionacionalaa.Models.GruposXEstado;
import com.programandounmundomejor.directorionacionalaa.R;

import java.util.HashMap;
import java.util.Map;

import static com.programandounmundomejor.directorionacionalaa.Clases.Global.codigoPostalSelect;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.coloniaSelect;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.estadoSelect;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstColonias;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstEstados;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstEstadosComplete;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstGruposXCP;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstGruposXEstado;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstMunicipios;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.municipioSelect;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.signature;


public class DireccionFragment extends Fragment {
    private EditText edtEstado, edtMunicipio, edtColonia, edtCodigoPostal;
    private Button btnSearhDireccion;
    private View view;

    //Variables globales
    private PostRequest postRequest = new PostRequest();
    private Callback callback = new Callback();
    private GlobalMethos globalMethos = new GlobalMethos();
    private int selectedItem = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_direccion, container, false);
        initViews();
        return view;
    }

    private void initViews(){
        edtEstado = (EditText) view.findViewById(R.id.edtEstado);
        edtMunicipio = (EditText) view.findViewById(R.id.edtMunicipio);
        edtColonia = (EditText) view.findViewById(R.id.edtColonia);
        edtCodigoPostal = (EditText) view.findViewById(R.id.edtCodigoPostal);

        btnSearhDireccion = (Button) view.findViewById(R.id.btnSearhDireccion);

        initActions();
    }

    private void initActions(){
        edtEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectEstado();
            }
        });

        edtMunicipio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectMunicipio();
            }
        });

        edtColonia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectColonia();
            }
        });

        btnSearhDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });
    }

    private void selectEstado(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                final String response = postRequest.enviarPost("signature="+signature, "searchEstados.php");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.processingResult("estados", response);
                        if(lstEstados.size() > 0){
                            //Creamos array de estados
                            String[] estadosArr = new String[lstEstados.size()];
                            estadosArr = lstEstados.toArray(estadosArr);
                            alertDialog(estadosArr, edtEstado, "estado");
                        } else {
                            Toast.makeText(getActivity(), "error en el servicio", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
        thread.start();
    }

    private void selectMunicipio(){
        if(estadoSelect != null){
            //Buscamos el idEstado
            final int idEstado = searchId(lstEstadosComplete, estadoSelect);
            if(idEstado > 0){
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        final String response = postRequest.enviarPost("signature="+signature+"&idEstado="+idEstado, "searchMunicipios.php");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback.processingResult("municipios", response);
                                if(lstMunicipios.size() > 0){
                                    //Creamos array de municipios
                                    String[] municipiosArr = new String[lstMunicipios.size()];
                                    municipiosArr = lstMunicipios.toArray(municipiosArr);
                                    alertDialog(municipiosArr, edtMunicipio, "municipio");
                                } else {
                                    Toast.makeText(getActivity(), "error en el servicio", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                };
                thread.start();
            } else {
                Toast.makeText(getActivity(), "error de estado", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Selecciona un estado", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectColonia(){
        if(estadoSelect != null){
            if(municipioSelect != null){
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        try{
                            estadoSelect = globalMethos.limpiarAcentos(estadoSelect);
                            municipioSelect = globalMethos.limpiarAcentos(municipioSelect);
                            String params = "signature="+signature+"&Estado="+estadoSelect + "&Municipio="+municipioSelect;
                            final String response = postRequest.enviarPost(params, "searchColonias.php");
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    callback.processingResult("colonias", response);
                                    if(lstColonias.size() > 0){
                                        //Creamos array de municipios
                                        String[] coloniasArr = new String[lstColonias.size()];
                                        coloniasArr = lstColonias.toArray(coloniasArr);
                                        alertDialog(coloniasArr, edtColonia, "colonia");
                                    } else {
                                        Toast.makeText(getActivity(), "error en el servicio", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } catch (Exception e){

                        }
                    }
                };
                thread.start();
            } else {
                Toast.makeText(getActivity(), "Selecciona un municipio", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Selecciona un estado", Toast.LENGTH_SHORT).show();
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
                    case "estado":
                        estadoSelect = array[selectedItem];
                        municipioSelect = null;
                        coloniaSelect = null;
                        edtMunicipio.setText("");
                        edtColonia.setText("");
                        break;
                    case "municipio":
                        municipioSelect = array[selectedItem];
                        coloniaSelect = null;
                        edtColonia.setText("");
                        break;
                    case "colonia":
                        coloniaSelect = array[selectedItem];
                        break;
                }

            }
        });

        cultureDialog.setNegativeButton("No seleccionar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (type){
                    case "estado":
                        estadoSelect = null;
                        municipioSelect = null;
                        coloniaSelect = null;
                        edtEstado.setText("");
                        edtMunicipio.setText("");
                        edtColonia.setText("");
                        break;
                    case "municipio":
                        municipioSelect = null;
                        coloniaSelect = null;
                        edtMunicipio.setText("");
                        edtColonia.setText("");
                        break;
                    case "colonia":
                        coloniaSelect = null;
                        edtColonia.setText("");
                        break;
                }
            }
        });

        //Display the Alert Dialog on app interface
        AlertDialog alertDialog = cultureDialog.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void validateForm(){
        if(!edtCodigoPostal.getText().toString().isEmpty()){
            codigoPostalSelect = edtCodigoPostal.getText().toString();
            searchGroupsXCP();
        } else {
            if(!edtEstado.getText().toString().isEmpty()){
                String params = "signature="+signature+"&estado="+estadoSelect;
                if(!edtMunicipio.getText().toString().isEmpty()){
                    params = params + "&municipio="+municipioSelect;
                    if(!edtColonia.getText().toString().isEmpty()){
                        params = params + "&colonia="+coloniaSelect;
                    }
                }
                searchGroupsXEstado(params);
            } else {
                Toast.makeText(getActivity(), "Debes ingresar valores", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void searchGroupsXCP(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                final String response = postRequest.enviarPost("signature="+signature+"&codigoPostal="+codigoPostalSelect, "searchGrupoXCodigoPostal.php");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.processingResult("gruposXCP", response);
                        if(lstGruposXCP.size() > 0){
                            //Creamos array de estados
                            Intent intent = new Intent(getActivity(), GruposXCPActivity.class);
                            getActivity().startActivity(intent);
                        } else {
                            Toast.makeText(getActivity(), "No se encontraron Grupos.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
        thread.start();
    }

    private void searchGroupsXEstado(final String params){
        Thread thread = new Thread(){
            @Override
            public void run() {
                final String response = postRequest.enviarPost(params, "searchGrupoXEstado.php");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.processingResult("gruposXEstado", response);
                        if(lstGruposXEstado.size() > 0){
                            //Creamos array de estados
                            Intent intent = new Intent(getActivity(), GruposXEstadoActivity.class);
                            getActivity().startActivity(intent);
                        } else {
                            Toast.makeText(getActivity(), "No se encontraron Grupos.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
        thread.start();
    }
}
