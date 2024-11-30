/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import MODELO.alumno;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author anemn
 */
public class Conexion {
    private static Connection conexion= null;
    private static PreparedStatement pstm;
    private static ResultSet rs;

    private Conexion() {
        // Busca el driver y establece la conexión
        try {
            Class.forName("org.postgresql.Driver");
            String URL = "jdbc:postgresql://localhost:5432/proyecto_tutorias";
            conexion = DriverManager.getConnection(URL, "postgres", "itsj");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el driver de PostgreSQL.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error: No se pudo conectar a la base de datos.");
            e.printStackTrace();
        }
    }

    public static Connection getConexion() {
        if (conexion == null) {
            new Conexion();
        }
        return conexion;
    }

    public static void cerrarConexion() {
        try {
            if (conexion != null) {
                conexion.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión.");
            e.printStackTrace();
        }
    }

    // Método para agregar un alumno
    public static boolean AgregarAlumno(alumno a) {
        String sql = "INSERT INTO alumno (NumeroControl, Nombre, PrimerAp, SegundoAp, Carrera, Semestre, Edad, Promedio) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstm = getConexion().prepareStatement(sql)) {
            pstm.setInt(1, a.getNumeroControl());
            pstm.setString(2, a.getNombre());
            pstm.setString(3, a.getPrimerAp());
            pstm.setString(4, a.getSegundorAp());
            pstm.setString(5, a.getCarrera());
            pstm.setInt(6, a.getSemestre());
            pstm.setInt(7, a.getEdad());
            pstm.setDouble(8, a.getPromedio());
            pstm.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error en AgregarAlumno:");
            e.printStackTrace();
        }
        return false;
    }

    // Método para eliminar un alumno
    public static boolean EliminarAlumno(String instruccion) {
        String sql = "DELETE FROM alumno WHERE NumeroControl = ?";
         try {
            String consulta = instruccion;
            pstm = conexion.prepareStatement(consulta);
            pstm.executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println("ERROR");
        }
        return false;
    }

    // Método para actualizar un alumno
    public static boolean ActualizarAlumno(alumno a) {
        String sql = "UPDATE alumno SET Nombre = ?, PrimerAp = ?, SegundoAp = ?, Carrera = ?, Semestre = ?, Edad = ?, Promedio = ? WHERE NumeroControl = ?";
        try (PreparedStatement pstm = getConexion().prepareStatement(sql)) {
            pstm.setString(1, a.getNombre());
            pstm.setString(2, a.getPrimerAp());
            pstm.setString(3, a.getSegundorAp());
            pstm.setString(4, a.getCarrera());
            pstm.setInt(5, a.getSemestre());
            pstm.setInt(6, a.getEdad());
            pstm.setDouble(7, a.getPromedio());
            pstm.setInt(8, a.getNumeroControl());
            pstm.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error en ActualizarAlumno:");
            e.printStackTrace();
        }
        return false;
    }

    // Método para buscar alumnos
    public static ResultSet BuscarAlumno(String consulta) {
        try {
            PreparedStatement pstm = getConexion().prepareStatement(consulta);
            return pstm.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error en BuscarAlumno:");
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
    Connection conexion = Conexion.getConexion();
   /* if (conexion != null) {
        System.out.println("Conexion exitosa");
    } else {
        System.out.println("No se pudo conectar a la base de datos.");
    }
   */ 
}

}//class
