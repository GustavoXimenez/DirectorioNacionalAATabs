package com.programandounmundomejor.directorionacionalaa.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.programandounmundomejor.directorionacionalaa.Clases.Callback;
import com.programandounmundomejor.directorionacionalaa.Clases.GetProgressDialog;
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
    private GetProgressDialog progressDialog = new GetProgressDialog();
    private int selectedItem = 0;

    private String estadoStr = "";
    private String municipioStr = "";
    private String coloniaStr = "";

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
        progressDialog.startProgressDialog(getActivity());
        Thread thread = new Thread(){
            @Override
            public void run() {
                final String response = postRequest.enviarPost("signature="+signature, "searchEstados.php");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.processingResult("estados", response);
                        progressDialog.stopProgressDialog(getActivity());
                        if(lstEstados.size() > 0){
                            //Creamos array de estados
                            String[] estadosArr = new String[lstEstados.size()];
                            estadosArr = lstEstados.toArray(estadosArr);
                            alertDialog(estadosArr, edtEstado, "estado");
                        } else {
                            messageSnackbar("No se encontraron estados", "Intente más tarde");
                        }
                    }
                });
            }
        };
        thread.start();
    }

    private void selectMunicipio(){
        if(estadoSelect != null){
            progressDialog.startProgressDialog(getActivity());
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
                                progressDialog.stopProgressDialog(getActivity());
                                if(lstMunicipios.size() > 0){
                                    //Creamos array de municipios
                                    String[] municipiosArr = new String[lstMunicipios.size()];
                                    municipiosArr = lstMunicipios.toArray(municipiosArr);
                                    alertDialog(municipiosArr, edtMunicipio, "municipio");
                                } else {
                                    messageSnackbar("No se encontraron municipios", "Intente más tarde");
                                }
                            }
                        });
                    }
                };
                thread.start();
            } else {
                messageSnackbar("error de estado", "Intente más nuevamente");
            }
        } else {
            messageSnackbar("Selecciona un estado", "Seleccionar");
        }
    }

    private void selectColonia(){
        if(estadoSelect != null){
            if(municipioSelect != null){
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        try{
                            estadoStr = globalMethos.limpiarAcentos(estadoSelect);
                            municipioStr = globalMethos.limpiarAcentos(municipioSelect);
                            //estadoSelect = globalMethos.limpiarAcentos(estadoSelect);
                            //municipioSelect = globalMethos.limpiarAcentos(municipioSelect);
                            String params = "signature="+signature+"&Estado="+estadoStr+"&Municipio="+municipioStr;
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
                                        messageSnackbar("No se encontraron colonias", "Intente más tarde");

                                    }
                                }
                            });
                        } catch (Exception e){

                        }
                    }
                };
                thread.start();
            } else {
                messageSnackbar("Selecciona un municipio", "Seleccionar");
            }
        } else {
            messageSnackbar("Selecciona un estado", "Seleccionar");
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
                estadoStr = globalMethos.limpiarAcentos(estadoSelect);
                String params = "signature="+signature+"&estado="+estadoStr;
                if(!edtMunicipio.getText().toString().isEmpty()){
                    municipioStr = globalMethos.limpiarAcentos(municipioSelect);
                    params = params + "&municipio="+municipioStr;
                    if(!edtColonia.getText().toString().isEmpty()){
                        coloniaStr = globalMethos.limpiarAcentos(coloniaSelect);
                        params = params + "&colonia="+coloniaStr;
                    }
                }
                searchGroupsXEstado(params);
            } else {
                messageSnackbar("Debes ingresar valores", "Llenar formulario");
            }
        }
    }

    private void searchGroupsXCP(){
        progressDialog.startProgressDialog(getActivity());
        Thread thread = new Thread(){
            @Override
            public void run() {
                final String response = postRequest.enviarPost("signature="+signature+"&codigoPostal="+codigoPostalSelect, "searchGrupoXCodigoPostal.php");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.processingResult("gruposXCP", response);
                        progressDialog.stopProgressDialog(getActivity());
                        if(lstGruposXCP.size() > 0){
                            //Creamos array de estados
                            Intent intent = new Intent(getActivity(), GruposXCPActivity.class);
                            getActivity().startActivity(intent);
                        } else {
                            //Snackbar.make(getActivity().findViewById(R.id.main_content), "No se encontraron Grupos", Snackbar.LENGTH_LONG).show();
                            messageSnackbar("No se encontraron Grupos", "Buscar nuevamente");
                        }
                    }
                });
            }
        };
        thread.start();
    }

    private void searchGroupsXEstado(final String params){
        progressDialog.startProgressDialog(getActivity());
        Thread thread = new Thread(){
            @Override
            public void run() {
                final String response = postRequest.enviarPost(params, "searchGrupoXEstado.php");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.processingResult("gruposXEstado", response);
                        progressDialog.stopProgressDialog(getActivity());
                        if(lstGruposXEstado.size() > 0){
                            //Creamos array de estados
                            Intent intent = new Intent(getActivity(), GruposXEstadoActivity.class);
                            getActivity().startActivity(intent);
                        } else {
                            messageSnackbar("No se encontraron Grupos", "Buscar nuevamente");
                        }
                    }
                });
            }
        };
        thread.start();
    }

    private void messageSnackbar(String messageError, String messageButton){
        Snackbar snackbar = Snackbar
                .make(getActivity().findViewById(R.id.main_content), messageError, Snackbar.LENGTH_LONG)
                .setAction(messageButton, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

        snackbar.show();
    }
}
