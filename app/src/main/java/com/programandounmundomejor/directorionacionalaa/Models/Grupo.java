package com.programandounmundomejor.directorionacionalaa.Models;

public class Grupo {

    private int IdGrupo;
    private String NombreGrupo;
    private String FechaInicio;
    private String Calle;
    private String NumExt;
    private String NumInt;
    private String Referencia;
    private String Colonia;
    private String MnpioDel;
    private String Estado;
    private String IdAreaDistrito;
    private String DescArea;


    public Grupo(int idGrupo, String nombreGrupo, String fechaInicio, String calle, String numExt,
                 String numInt, String referencia, String colonia, String mnpioDel, String estado,
                 String idAreaDistrito, String descArea) {
        IdGrupo = idGrupo;
        NombreGrupo = nombreGrupo;
        FechaInicio = fechaInicio;
        Calle = calle;
        NumExt = numExt;
        NumInt = numInt;
        Referencia = referencia;
        Colonia = colonia;
        MnpioDel = mnpioDel;
        Estado = estado;
        IdAreaDistrito = idAreaDistrito;
        DescArea = descArea;
    }

    public int getIdGrupo() {
        return IdGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        IdGrupo = idGrupo;
    }

    public String getNombreGrupo() {
        return NombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        NombreGrupo = nombreGrupo;
    }

    public String getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        FechaInicio = fechaInicio;
    }

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String calle) {
        Calle = calle;
    }

    public String getNumExt() {
        return NumExt;
    }

    public void setNumExt(String numExt) {
        NumExt = numExt;
    }

    public String getNumInt() {
        return NumInt;
    }

    public void setNumInt(String numInt) {
        NumInt = numInt;
    }

    public String getReferencia() {
        return Referencia;
    }

    public void setReferencia(String referencia) {
        Referencia = referencia;
    }

    public String getColonia() {
        return Colonia;
    }

    public void setColonia(String colonia) {
        Colonia = colonia;
    }

    public String getMnpioDel() {
        return MnpioDel;
    }

    public void setMnpioDel(String mnpioDel) {
        MnpioDel = mnpioDel;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getIdAreaDistrito() {
        return IdAreaDistrito;
    }

    public void setIdAreaDistrito(String idAreaDistrito) {
        IdAreaDistrito = idAreaDistrito;
    }

    public String getDescArea() {
        return DescArea;
    }

    public void setDescArea(String descArea) {
        DescArea = descArea;
    }
}
