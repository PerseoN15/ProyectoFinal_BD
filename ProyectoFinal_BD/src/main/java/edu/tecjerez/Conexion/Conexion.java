/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

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

    private Conexion(){
        // Busa el driver para conectarse a postgrate
        try {
            Class.forName("org.postgresql.Driver");
            String URL = "jdbc:postgresql://localhost:5432/proyecto_Tutorias";
            conexion = DriverManager.getConnection( URL,"root","pass123" );
        } catch (ClassNotFoundException e) {
            System.out.println("Error en el controlador de conexión a PostgreSQL");
        } catch (SQLException e) {
            System.out.println("Error en la ruta de conexión");
        }
    }

    public static Connection getConexion(){
        if (conexion == null){
            new Conexion();
        }
        return conexion;
    }

    static void cerrarConexion(){
        try {
            if (pstm != null) {
                pstm.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }catch (SQLException e){
            System.out.println("Error al cerrar la conexión");
            e.printStackTrace();
        }
    }

    //======================================METODO ABCC==============================================================
    //ALTAS
    public static boolean AgregarAlumno(alumno a){
        try {
            Connection conexion = getConexion();
            pstm = conexion.prepareStatement("INSERT INTO paciente VALUES (?,?,?,?,?,?,?,?)");

            pstm.setInt(1, a.getNumeroControl());
            pstm.setString(2, a.getNombre());
            pstm.setString(4, a.getPrimerAp());
            pstm.setString(5, a.getsegundoAp());
            pstm.setString(6, a.getCarrera());
            pstm.setInt(7, a.getSemestre());
            pstm.setInt(8, a.getEdad());
            pstm.setDouble(8, a.getPromedio());
            pstm.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error en AgregarAlumno en Conexion.java");
            e.printStackTrace();
        }
        return false;
    }
//==================================Bajas========================================
    public static boolean EliminarAlumno(String instruccion){
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
//====================================Cambios================================================
    public static boolean ActualizarAlumno(alumno a){
        try {
            Connection conexion = getConexion();
            pstm = conexion.prepareStatement("UPDATE alumnos SET Nombre=?,PrimerAp=?,SegundoAp=?,Carrera=?,Semestre=?,Edad=?,Promedio=? WHERE NumeroControl=" + a.getNumeroControl());
            pstm.setDouble(1, a.getNombre());
            pstm.setString(2, a.getPrimerAp());
            pstm.setString(3, a.getSegundoAp());
            pstm.setString(4, a.getCarrera());
            pstm.setString(5, a.getSemestre());
            pstm.setString(6, a.getEdad());
            pstm.setString(7, a.getPromedio());
            pstm.executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return false;
    }
//==================================Consultas==================================================
    public static ResultSet BuscarAlumno(String consulta){
        try {
            pstm = conexion.prepareStatement(consulta);
            return pstm.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error en instrucción SQL");
        }
        return null;
    }

    public static void main(String[] args) {
        new Conexion();
    }
}//class
