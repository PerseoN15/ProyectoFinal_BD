/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

/**
 *
 * @author anemn
 */
public class Memento {
    private alumno estado;

    public Memento(alumno estado) {
        this.estado = estado;
    }

    public alumno getEstado() {
        return estado;
    }
}