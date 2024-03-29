package com.programandounmundomejor.directorionacionalaa.Clases;

import com.programandounmundomejor.directorionacionalaa.Models.Grupo;
import com.programandounmundomejor.directorionacionalaa.Models.GruposXArea;
import com.programandounmundomejor.directorionacionalaa.Models.GruposXCP;
import com.programandounmundomejor.directorionacionalaa.Models.GruposXEstado;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Global {
    // Servicio
    //public static String system_WServices = "http://172.27.48.92/WebServiceAA/";
    public static String system_WServices = "http://aa.org.mx/WebServiceAA/";
    public static String signature = "ProgramandoUnMundoMejor";

    // Listas Combos
    public static List<String> lstEstados = new ArrayList<>();
    public static List<String> lstMunicipios = new ArrayList<>();
    public static List<String> lstColonias = new ArrayList<>();
    public static List<String> lstAreas = new ArrayList<>();
    public static List<String> lstDistritos = new ArrayList<>();

    // Listas Completas Combos
    public static HashMap<Integer, String> lstEstadosComplete = new HashMap<>();
    public static HashMap<Integer, String> lstMunicipiosComplete = new HashMap<>();
    public static HashMap<Integer, String> lstColoniasComplete = new HashMap<>();
    public static HashMap<Integer, String> lstAreasComplete = new HashMap<>();
    public static HashMap<Integer, String> lstDistritosComplete = new HashMap<>();

    // Listas de Objetos de Grupos
    public static List<GruposXCP> lstGruposXCP = new ArrayList<>();
    public static List<GruposXEstado> lstGruposXEstado = new ArrayList<>();
    public static List<GruposXArea> lstGruposXArea = new ArrayList<>();
    public static List<Grupo> lstGrupo = new ArrayList<>();
    public static List<List<String>> lstHorarios = new ArrayList<>();


    // Elements Selections
    public static String estadoSelect;
    public static String municipioSelect;
    public static String coloniaSelect;
    public static String areaSelect;
    public static String distritosSelect;
    public static String codigoPostalSelect;
    public static String areaId;
    public static String distritosId;
}
