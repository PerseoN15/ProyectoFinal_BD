/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Conexion.Conexion;
import MODELO.alumno;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author anemn
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AlumnoDAO {

    // Método para agregar un alumno
    public boolean agregarAlumno(alumno a) {
        return Conexion.AgregarAlumno(a);
    }

    // Método para eliminar un alumno por su Número de Control
    public boolean eliminarAlumno(String numeroControl) {
        String sql = "DELETE FROM alumno WHERE NumeroControl = '" + numeroControl + "'";
        return Conexion.EliminarAlumno(sql);
    }

    // Método para actualizar los datos de un alumno
    public boolean actualizarAlumno(alumno a) {
        return Conexion.ActualizarAlumno(a);
    }

    // Método para buscar alumnos
    public ArrayList<alumno> buscarAlumno(String filtro) {
        ArrayList<alumno> listaAlumnos = new ArrayList<>();
        String sql = "SELECT * FROM alumno ORDER BY NumeroControl";

        // Verifica si la conexión está inicializada
        if (Conexion.getConexion() == null) {
            System.out.println("La conexión NO está iniciada");
            return listaAlumnos;
        }

        try {
            // Ejecuta la consulta
            ResultSet rs = Conexion.BuscarAlumno(sql);
            if (rs == null) {
                System.out.println("El ResultSet es nulo");
                return listaAlumnos;
            }

            // Itera sobre el resultado y agrega alumnos a la lista
            while (rs.next()) {
                int numeroControl = rs.getInt("NumeroControl");
                String nombre = rs.getString("Nombre");
                String primerAp = rs.getString("PrimerAp");
                String segundoAp = rs.getString("SegundoAp");
                String carrera = rs.getString("Carrera");
                int semestre = rs.getInt("Semestre");
                int edad = rs.getInt("Edad");
                double promedio = rs.getDouble("Promedio");

                listaAlumnos.add(new alumno(numeroControl, promedio, nombre, primerAp, segundoAp, carrera, semestre, edad));
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar alumnos:");
            e.printStackTrace();
        }

        return listaAlumnos;
    }
}


