package com.jitgad.bjitgad.Models;

/**
 *
 * @author jorge
 */
public class HistorialjugadoresModel {

    private String id_historial_jugador = "-2";
    private String id_tema_actividad = "-2";
    private String id_jugador = "-2";
    private String hst_descripcion = "-2";
    private String hst_fecha = "-2";
    private String hst_tiempo = "-2";
    private String hst_interacciones = "-2";

    public HistorialjugadoresModel() {
    }

    public HistorialjugadoresModel(String Id_historial_jugador,
            String Id_tema_actividad,
            String Id_jugador,
            String Hst_descripcion,
            String Hst_fecha,
            String Hst_tiempo,
            String Hst_interacciones) {
        this.id_historial_jugador = Id_historial_jugador;
        this.id_tema_actividad = Id_tema_actividad;
        this.id_jugador = Id_jugador;
        this.hst_descripcion = Hst_descripcion;
        this.hst_fecha = Hst_fecha;
        this.hst_tiempo = Hst_tiempo;
        this.hst_interacciones = Hst_interacciones;
    }

    public String getId_historial_jugador() {
        return id_historial_jugador;
    }

    public void setId_historial_jugador(String id_historial_jugador) {
        this.id_historial_jugador = id_historial_jugador;
    }

    public String getId_tema_actividad() {
        return id_tema_actividad;
    }

    public void setId_tema_actividad(String id_tema_actividad) {
        this.id_tema_actividad = id_tema_actividad;
    }

    public String getId_jugador() {
        return id_jugador;
    }

    public void setId_jugador(String id_jugador) {
        this.id_jugador = id_jugador;
    }

    public String getHst_descripcion() {
        return hst_descripcion;
    }

    public void setHst_descripcion(String hst_descripcion) {
        this.hst_descripcion = hst_descripcion;
    }

    public String getHst_fecha() {
        return hst_fecha;
    }

    public void setHst_fecha(String hst_fecha) {
        this.hst_fecha = hst_fecha;
    }

    public String getHst_tiempo() {
        return hst_tiempo;
    }

    public void setHst_tiempo(String hst_tiempo) {
        this.hst_tiempo = hst_tiempo;
    }

    public String getHst_interacciones() {
        return hst_interacciones;
    }

    public void setHst_interacciones(String hst_interacciones) {
        this.hst_interacciones = hst_interacciones;
    }

}
