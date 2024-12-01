package Conexion;

import MODELO.alumno;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexion {
    private static Connection conexion = null;

    private Conexion() {
        try {
            Class.forName("org.postgresql.Driver");
            String URL = "jdbc:postgresql://localhost:5432/proyecto_tutorias";
            conexion = DriverManager.getConnection(URL, "postgres", "itsj");
            System.out.println("Conexión establecida correctamente.");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver de PostgreSQL.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error: No se pudo conectar a la base de datos.");
            e.printStackTrace();
        }
    }

    public static Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                new Conexion();
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar el estado de la conexión.");
            e.printStackTrace();
        }
        return conexion;
    }

    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión.");
            e.printStackTrace();
        }
    }

    public static boolean AgregarAlumno(alumno a) {
        String sql = "INSERT INTO alumno (NumeroControl, Nombre, PrimerAp, SegundoAp, Carrera, Semestre, Edad, Promedio) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexion = getConexion();
             PreparedStatement pstm = conexion.prepareStatement(sql)) {

            if (conexion == null || conexion.isClosed()) {
                System.err.println("Error: Conexión no establecida.");
                return false;
            }

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
            System.err.println("Error en AgregarAlumno:");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean EliminarAlumno(String numeroControl) {
        String sql = "DELETE FROM alumno WHERE NumeroControl = ?";
        try (Connection conexion = getConexion();
             PreparedStatement pstm = conexion.prepareStatement(sql)) {

            if (conexion == null || conexion.isClosed()) {
                System.err.println("Error: Conexión no establecida.");
                return false;
            }

            pstm.setInt(1, Integer.parseInt(numeroControl));
            pstm.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error en EliminarAlumno:");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean ActualizarAlumno(alumno a) {
        String sql = "UPDATE alumno SET Nombre = ?, PrimerAp = ?, SegundoAp = ?, Carrera = ?, Semestre = ?, Edad = ?, Promedio = ? WHERE NumeroControl = ?";
        try (Connection conexion = getConexion();
             PreparedStatement pstm = conexion.prepareStatement(sql)) {

            if (conexion == null || conexion.isClosed()) {
                System.err.println("Error: Conexión no establecida.");
                return false;
            }

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
            System.err.println("Error en ActualizarAlumno:");
            e.printStackTrace();
        }
        return false;
    }

    public static ResultSet BuscarAlumno(String consulta) {
        try {
            Connection conexion = getConexion();
            if (conexion == null || conexion.isClosed()) {
                System.err.println("Error: Conexión no establecida.");
                return null;
            }
            PreparedStatement pstm = conexion.prepareStatement(consulta);
            return pstm.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error en BuscarAlumno:");
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Connection conexion = Conexion.getConexion();
        if (conexion != null) {
            System.out.println("Conexión exitosa.");
        } else {
            System.err.println("No se pudo conectar a la base de datos.");
        }
    }
}
