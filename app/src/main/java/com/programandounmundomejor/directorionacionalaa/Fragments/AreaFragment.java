package com.programandounmundomejor.directorionacionalaa.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.programandounmundomejor.directorionacionalaa.Clases.PostRequest;
import com.programandounmundomejor.directorionacionalaa.GruposXAreaActivity;
import com.programandounmundomejor.directorionacionalaa.R;

import java.util.HashMap;
import java.util.Map;

import static com.programandounmundomejor.directorionacionalaa.Clases.Global.areaSelect;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.distritosSelect;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstAreas;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstAreasComplete;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstDistritos;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstGruposXArea;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.signature;


public class AreaFragment extends Fragment {

    private EditText edtArea, edtDistrito;
    private Button btnSearhGrupos;
    private View view;

    //Variables globales
    private PostRequest postRequest = new PostRequest();
    private Callback callback = new Callback();
    private GetProgressDialog progressDialog = new GetProgressDialog();
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
        btnSearhGrupos = (Button) view.findViewById(R.id.btnSearhGrupos);

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

        btnSearhGrupos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtArea.getText().toString().isEmpty()){
                    final int idArea = searchId(lstAreasComplete, areaSelect);
                    String params = "signature="+signature+"&idArea="+idArea;
                    if(!edtDistrito.getText().toString().isEmpty()){
                        params = params + "&idDistrito="+distritosSelect;
                    }
                    searchGroupsXArea(params);
                } else {
                    messageSnackbar("Debes seleccionar un Área", "Seleccionar");
                }
            }
        });
    }

    private void selectArea(){
        progressDialog.startProgressDialog(getActivity());
        Thread thread = new Thread(){
            @Override
            public void run() {
                final String response = postRequest.enviarPost("signature="+signature, "searchAreas.php");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.processingResult("areas", response);
                        progressDialog.stopProgressDialog(getActivity());
                        if(lstAreas.size() > 0){
                            //Creamos array de estados
                            String[] areasArr = new String[lstAreas.size()];
                            areasArr = lstAreas.toArray(areasArr);
                            alertDialog(areasArr, edtArea, "area");
                        } else {
                            messageSnackbar("Problemas de red", "Intentar más tarde");

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
                progressDialog.startProgressDialog(getActivity());
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        final String response = postRequest.enviarPost("signature="+signature+"&idArea="+idArea, "searchDistritos.php");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback.processingResult("distritos", response);
                                progressDialog.stopProgressDialog(getActivity());
                                if(lstDistritos.size() > 0){
                                    //Creamos array de municipios
                                    String[] distritosArr = new String[lstDistritos.size()];
                                    distritosArr = lstDistritos.toArray(distritosArr);
                                    alertDialog(distritosArr, edtDistrito, "distrito");
                                } else {
                                    messageSnackbar("Problemas de red", "Intentar más tarde");
                                }
                            }
                        });
                    }
                };
                thread.start();
            } else {
                messageSnackbar("Problemas de red", "Intentar más tarde");
            }
        } else {
            messageSnackbar("Selecciona una area", "Seleccionar");
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

        cultureDialog.setNegativeButton("No seleccionar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (type){
                    case "area":
                        areaSelect = null;
                        distritosSelect = null;
                        edtArea.setText("");
                        edtDistrito.setText("");
                        break;
                    case "distrito":
                        distritosSelect = null;
                        edtDistrito.setText("");
                        break;
                }
            }
        });

        //Display the Alert Dialog on app interface
        AlertDialog alertDialog = cultureDialog.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void searchGroupsXArea(final String params){
        progressDialog.startProgressDialog(getActivity());
        Thread thread = new Thread(){
            @Override
            public void run() {
                final String response = postRequest.enviarPost(params, "searchGrupoXArea.php");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.processingResult("gruposXArea", response);
                        progressDialog.stopProgressDialog(getActivity());
                        if(lstGruposXArea.size() > 0){
                            //Creamos array de estados
                            Intent intent = new Intent(getActivity(), GruposXAreaActivity.class);
                            getActivity().startActivity(intent);
                        } else {
                            messageSnackbar("No se encontraron Grupos.", "Cambiar datos");
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
