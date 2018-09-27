package com.programandounmundomejor.directorionacionalaa.Models;

public class GruposXArea {
    private int idGrupo;
    private String nombreGrupo;
    private String municipio;
    private String colonia;
    private String estado;
    private String area;
    private String distrito;

    public GruposXArea(int idGrupo, String nombreGrupo, String colonia, String municipio, String estado, String area, String distrito){
        this.idGrupo = idGrupo;
        this.nombreGrupo = nombreGrupo;
        this.municipio = municipio;
        this.colonia = colonia;
        this.estado = estado;
        this.area = area;
        this.distrito = distrito;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getEstado() {
        return colonia;
    }

    public void setEstado(String colonia) {
        this.colonia = colonia;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }
}
