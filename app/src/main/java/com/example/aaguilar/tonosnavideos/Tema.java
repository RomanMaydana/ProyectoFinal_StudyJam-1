package com.example.aaguilar.tonosnavideos;

/**
 * Created by Antonio Aguilar on 12/21/16.
 */

public class Tema {
    private String titulo;
    private String duracion;
    private int icono;
    private int cancion;
    private String insercion;

    public Tema(String titulo, String duracion, int icono, int cancion, String insercion) {
        this.titulo = titulo;
        this.duracion = duracion;
        this.icono = icono;
        this.cancion = cancion;
        this.insercion = insercion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }

    public int getCancion() {
        return cancion;
    }

    public void setCancion(int cancion) {
        this.cancion = cancion;
    }

    public String getInsercion() {
        return insercion;
    }

    public void setInsercion(String insercion) {
        this.insercion = insercion;
    }


}
