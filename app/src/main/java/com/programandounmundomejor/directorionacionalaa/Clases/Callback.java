package com.programandounmundomejor.directorionacionalaa.Clases;

import com.programandounmundomejor.directorionacionalaa.Models.GruposXCP;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstAreas;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstAreasComplete;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstColonias;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstColoniasComplete;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstDistritos;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstDistritosComplete;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstEstados;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstEstadosComplete;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstGruposXCP;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstMunicipios;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstMunicipiosComplete;

public class Callback {
    public void processingResult(String type, String result){
        switch (type){
            case "estados":
                parseJSONEstados(result);
                break;
            case "municipios":
                parseJSONMunicipios(result);
                break;
            case "colonias":
                parseJSONColonias(result);
                break;
            case "areas":
                parseJSONAreas(result);
                break;
            case "distritos":
                parseJSONDistritos(result);
                break;
            case "gruposXCP":
                parseJSONGruposXCP(result);
                break;

        }
    }

    private void parseJSONEstados(String result){
        try{
            lstEstados.clear();
            lstEstadosComplete.clear();
            JSONArray jsonEstados = new JSONArray(result);
            for(int i = 0; i < jsonEstados.length(); i++){
                JSONObject objectEstados = jsonEstados.getJSONObject(i);
                lstEstados.add(objectEstados.getString("Estado"));
                lstEstadosComplete.put(objectEstados.getInt("IdEstado"), objectEstados.getString("Estado"));
            }
        } catch (Exception e){
            e.getMessage();
        }
    }

    private void parseJSONMunicipios(String result){
        try{
            lstMunicipios.clear();
            lstMunicipiosComplete.clear();
            JSONArray jsonMunicipios = new JSONArray(result);
            for(int i = 0; i < jsonMunicipios.length(); i++){
                JSONObject objectMunicipios = jsonMunicipios.getJSONObject(i);
                lstMunicipios.add(objectMunicipios.getString("Municipio"));
                lstMunicipiosComplete.put(objectMunicipios.getInt("IdMunicipio"), objectMunicipios.getString("Municipio"));
            }
        } catch (Exception e){
            e.getMessage();
        }
    }

    private void parseJSONColonias(String result){
        try{
            lstColonias.clear();
            lstColoniasComplete.clear();
            JSONArray jsonColonias = new JSONArray(result);
            for(int i = 0; i < jsonColonias.length(); i++){
                JSONObject objectColonias = jsonColonias.getJSONObject(i);
                lstColonias.add(objectColonias.getString("Colonia"));
                lstColoniasComplete.put(objectColonias.getInt("IdCodigoPostal"), objectColonias.getString("Colonia"));
            }
        } catch (Exception e){
            e.getMessage();
        }
    }

    private void parseJSONAreas(String result){
        try{
            lstAreas.clear();
            lstAreasComplete.clear();
            JSONArray jsonAreas = new JSONArray(result);
            for(int i = 0; i < jsonAreas.length(); i++){
                JSONObject objectAreas = jsonAreas.getJSONObject(i);
                lstAreas.add(objectAreas.getString("DescArea"));
                lstAreasComplete.put(objectAreas.getInt("IdArea"), objectAreas.getString("DescArea"));
            }
        } catch (Exception e){
            e.getMessage();
        }
    }

    private void parseJSONDistritos(String result){
        try{
            lstDistritos.clear();
            lstDistritosComplete.clear();
            JSONArray jsonDistritos = new JSONArray(result);
            for(int i = 0; i < jsonDistritos.length(); i++){
                JSONObject objectDistritos = jsonDistritos.getJSONObject(i);
                lstDistritos.add(objectDistritos.getString("Distrito"));
                lstDistritosComplete.put(objectDistritos.getInt("IdAreaDistrito"), objectDistritos.getString("Distrito"));
            }
        } catch (Exception e){
            e.getMessage();
        }
    }

    private void parseJSONGruposXCP(String result){
        try{
            lstGruposXCP.clear();
            JSONArray jsonGrupos = new JSONArray(result);
            for(int i = 0; i < jsonGrupos.length(); i++){
                JSONObject objectGrupos = jsonGrupos.getJSONObject(i);
                lstGruposXCP.add(new GruposXCP(objectGrupos.getInt("IdGrupo"),
                        objectGrupos.getString("NombreGrupo"),
                        objectGrupos.getString("Colonia"),
                        objectGrupos.getString("MnpioDel"),
                        objectGrupos.getString("Estado")));
            }
        } catch (Exception e){
            e.getMessage();
        }
    }
}
