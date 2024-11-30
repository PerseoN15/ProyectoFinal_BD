/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

/**
 *
 * @author anemn
 */
public class alumno {
    private int NumeroControl, Semestre, Edad;
    private String Nombre, PrimerAp,segundoAp, Carrera;
    private double Promedio;

    public alumno(int NumeroControl, double Promedio, String Nombre, String PrimerAp,String segundoAp, String Carrera, int Semestre, int Edad) {
        this.NumeroControl = NumeroControl;
        this.Nombre = Nombre;
        this.PrimerAp = PrimerAp;
        this.segundoAp = segundoAp;
        this.Carrera = Carrera;
        this.Semestre = Semestre;
        this.Edad = Edad;
        this.Promedio = Promedio;
    }

    public alumno() {
        
    }

    public String getPrimerAp() {
        return PrimerAp;
    }

    public void setPrimerAp(String PrimerAp) {
        this.PrimerAp = PrimerAp;
    }

   public String getSegundorAp() {
        return segundoAp;
    }

    public void setSegundoAp(String SegundoAp) {
        this.segundoAp = SegundoAp;
    }

    public int getNumeroControl() {
        return NumeroControl;
    }

    public void setNumeroControl(int NumeroControl) {
        this.NumeroControl = NumeroControl;
    }

    public double getPromedio() {
        return Promedio;
    }

    public void setPromedio(int Promedio) {
        this.Promedio = Promedio;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getCarrera() {
        return Carrera;
    }

    public void setCarrera(String Carrera) {
        this.Carrera = Carrera;
    }

    public int getSemestre() {
        return Semestre;
    }

    public void setSemestre(int Semestre) {
        this.Semestre = Semestre;
    }

    public int getEdad() {
        return Edad;
    }

    public void setEdad(int Edad) {
        this.Edad = Edad;
    }

    
}// class
