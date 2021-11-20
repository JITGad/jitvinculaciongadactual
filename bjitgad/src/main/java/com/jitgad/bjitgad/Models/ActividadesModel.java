package com.jitgad.bjitgad.Models;

/**
 *
 * @author jorge
 */
public class ActividadesModel {

    private String id_actividad = "-2";
    private String act_nombre = "-2";
    private String act_interacciones = "-2";
    private String act_icono = "-2";

    public ActividadesModel() {
    }

    public ActividadesModel(String Id_actividad, String Act_nombre, String Act_interacciones, String Act_icono) {
        this.id_actividad = Id_actividad;
        this.act_nombre = Act_nombre;
        this.act_interacciones = Act_interacciones;
        this.act_icono = Act_icono;
    }

    public String getId_actividad() {
        return id_actividad;
    }

    public void setId_actividad(String id_actividad) {
        this.id_actividad = id_actividad;
    }

    public String getAct_nombre() {
        return act_nombre;
    }

    public void setAct_nombre(String act_nombre) {
        this.act_nombre = act_nombre;
    }

    public String getAct_interacciones() {
        return act_interacciones;
    }

    public void setAct_interacciones(String act_interacciones) {
        this.act_interacciones = act_interacciones;
    }

    public String getAct_icono() {
        return act_icono;
    }

    public void setAct_icono(String act_icono) {
        this.act_icono = act_icono;
    }

}
