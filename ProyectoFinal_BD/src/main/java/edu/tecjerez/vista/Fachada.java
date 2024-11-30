/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;
import Controlador.AlumnoDAO;
import Conexion.Conexion;
import static Conexion.Conexion.getConexion;
import MODELO.alumno;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author anemn
 */
public class Fachada {
    private static Fachada instancia = null;
    private static PreparedStatement pstm;
    private Connection conexion;
    private AlumnoDAO aDAo;
    private alumno alum;
    
    Fachada(){
        this.conexion =  Conexion.getConexion();
        this.aDAo =  new AlumnoDAO();
        this.alum = new alumno();
        
    }
    
    public static Fachada obtenerInstancia(){
         if (instancia == null) {
            instancia = new Fachada();
        }
        return instancia;
        
    }
    
    //====================================================================================================================
    public static boolean FachadaAgregarAlumno(alumno alum){
        Connection conexion = getConexion();
        try {
            conexion.setAutoCommit(false);
            pstm = conexion.prepareStatement("UPDATE alumno SET Nombre = ?, PrimerAp = ?, SegundoAp = ?, Carrera = ?, Semestre = ?, Edad = ?, Promedio = ? WHERE NumeroControl = ?");
pstm.setString(1, alum.getNombre());
pstm.setString(2, alum.getPrimerAp());
pstm.setString(3, alum.getSegundorAp());
pstm.setString(4, alum.getCarrera());
pstm.setInt(5, alum.getSemestre());
pstm.setInt(6, alum.getEdad());
pstm.setDouble(7, alum.getPromedio());
pstm.setInt(8, alum.getNumeroControl());

            pstm.executeUpdate();
            
            conexion.commit();
            return true;
        } catch (SQLException e) {
            System.out.println("Error en AgregarPaciente en Fachada.java");
            e.printStackTrace();
            
            try {
                conexion.rollback();
            } catch (SQLException a) {
                System.out.println("Error al hacer el rollback");
                a.printStackTrace();
            }
        } finally{
            try {
                conexion.setAutoCommit(true);
                if(pstm !=null){
                    pstm.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
                }
        return false;
    }// metodo FachadaAgregarAlumno
        //====================================================================================================================

    public static boolean FachadaAgregarAlumnos(alumno alum) {
    Connection conexion = getConexion();
    try {
        conexion.setAutoCommit(false);

        // Verificar si ya existe el alumno
        String checkQuery = "SELECT COUNT(*) FROM alumno WHERE NumeroControl = ?";
        try (PreparedStatement checkStmt = conexion.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, alum.getNumeroControl());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                // Si el alumno ya existe, ejecuta un UPDATE
                String updateQuery = "UPDATE alumno SET Nombre = ?, PrimerAp = ?, SegundoAp = ?, Carrera = ?, Semestre = ?, Edad = ?, Promedio = ? WHERE NumeroControl = ?";
                try (PreparedStatement updateStmt = conexion.prepareStatement(updateQuery)) {
                    updateStmt.setString(1, alum.getNombre());
                    updateStmt.setString(2, alum.getPrimerAp());
                    updateStmt.setString(3, alum.getSegundorAp());
                    updateStmt.setString(4, alum.getCarrera());
                    updateStmt.setInt(5, alum.getSemestre());
                    updateStmt.setInt(6, alum.getEdad());
                    updateStmt.setDouble(7, alum.getPromedio());
                    updateStmt.setInt(8, alum.getNumeroControl());
                    updateStmt.executeUpdate();
                }
            } else {
                // Si el alumno no existe, ejecuta un INSERT
                String insertQuery = "INSERT INTO alumno (NumeroControl, Nombre, PrimerAp, SegundoAp, Carrera, Semestre, Edad, Promedio) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement insertStmt = conexion.prepareStatement(insertQuery)) {
                    insertStmt.setInt(1, alum.getNumeroControl());
                    insertStmt.setString(2, alum.getNombre());
                    insertStmt.setString(3, alum.getPrimerAp());
                    insertStmt.setString(4, alum.getSegundorAp());
                    insertStmt.setString(5, alum.getCarrera());
                    insertStmt.setInt(6, alum.getSemestre());
                    insertStmt.setInt(7, alum.getEdad());
                    insertStmt.setDouble(8, alum.getPromedio());
                    insertStmt.executeUpdate();
                }
            }
        }

        conexion.commit();
        return true;
    } catch (SQLException e) {
        System.out.println("Error en FachadaAgregarAlumnos en archivo Fachada.java");
        e.printStackTrace();
        try {
            conexion.rollback();
        } catch (SQLException ex) {
            System.out.println("Error al hacer el rollback");
            ex.printStackTrace();
        }
    } finally {
        try {
            conexion.setAutoCommit(true);
            if (pstm != null) {
                pstm.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return false;
}


        //====================================================================================================================

  
    public static boolean FachadaEliminarAlumno(String filtro) {
    boolean res = false;
    Connection conexion = getConexion();
    try {
        conexion.setAutoCommit(false);

        // Asegúrate de usar una comparación correcta para el tipo de dato
        String sql = "DELETE FROM alumno WHERE NumeroControl = ?";
        pstm = conexion.prepareStatement(sql);

        // Convertir el filtro a entero si es necesario
        pstm.setInt(1, Integer.parseInt(filtro));

        // Ejecutar la consulta
        int rowsAffected = pstm.executeUpdate();

        if (rowsAffected > 0) {
            res = true;
            conexion.commit();
        } else {
            System.out.println("No se encontró ningún alumno con el Número de Control proporcionado.");
        }
    } catch (NumberFormatException e) {
        System.out.println("Error: El filtro proporcionado no es un número válido. " + e.getMessage());
    } catch (SQLException ex) {
        System.out.println("Error en FachadaEliminarAlumno: " + ex.getMessage());
        try {
            conexion.rollback();
        } catch (SQLException e) {
            System.out.println("Error al hacer el rollback en FachadaEliminarAlumno: " + e.getMessage());
        }
    } finally {
        try {
            conexion.setAutoCommit(true);
            if (pstm != null) {
                pstm.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return res;
}


            //====================================================================================================================

    
        public static boolean FachadaEliminarAlumno2(String filtro){
        boolean res = false;
        Connection conexion = getConexion();
        try {
            conexion.setAutoCommit(false);
            String sql= "DELETE from respaldo where nombre = '"+filtro+"'";
            pstm = conexion.prepareStatement(sql);
            pstm.executeUpdate();
            res= true;
            conexion.commit();
        } catch (Exception ex) {
            System.out.println("ERROR");
            try {
                conexion.rollback();
            } catch (SQLException e) {
                System.out.println("Error al hacer el rollback de eliminacion");
                e.printStackTrace();
            }
        } finally{
            try {
                conexion.setAutoCommit(true);
                if(pstm != null){
                    pstm.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return res;

    }
        //====================================================================================================================

    public static boolean FachadaActualizarAlumnos(alumno alum){
        Connection conexion = getConexion();
        
        try {
            conexion.setAutoCommit(false); //CON ESTO INICIA LA TRANSACCION 
            pstm = conexion.prepareStatement("UPDATE alumno SET Nombre = ?, PrimerAp = ?, SegundoAp = ?, Carrera = ?, Semestre = ?, Edad = ?, Promedio = ? WHERE NumeroControl = ?");
            pstm.setString(1, alum.getNombre());
            pstm.setString(2, alum.getPrimerAp());
            pstm.setString(3, alum.getSegundorAp());
            pstm.setString(4, alum.getCarrera());
            pstm.setInt(5, alum.getSemestre());
            pstm.setInt(6, alum.getEdad());
            pstm.setDouble(7, alum.getPromedio());
            pstm.setInt(8, alum.getNumeroControl());

            pstm.executeUpdate();
            
            conexion.commit(); // AQUI SE GENERA EL COMMIT
            return true;
        } catch (SQLException e) {
            System.out.println("Error en ActualizarPaciente en Fachada.java");
            e.printStackTrace();
            try {
                conexion.rollback(); // EN CASO DE ERROR AQUI SE GENERA EL ROLLBACK
            } catch (SQLException ex) {
                System.out.println("Error en el rollback de acutalizaciones");
                ex.printStackTrace();
            }
            
        } finally {
            try {
                conexion.setAutoCommit(true);
                if(pstm != null){
                    pstm.close();
                }
            } catch (SQLException m) {
                m.printStackTrace();
            }
        }
        return false;
    }
    
      //====================================================================================================================
  
   /* public static void generarInforme(JFrame parentFrame) {
        try {
           String reportPath = "C:\\Users\\JaspersoftWorkspace\\MyReports\\REPORTE_PACIENTE.jasper";

            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportPath);

            String url = "jdbc:postgresql://localhost:5432/";
            String user = "root";
            String password = "itsj";
            Connection connection = DriverManager.getConnection(url, user, password);

            Map<String, Object> parametros = new HashMap<>();

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, connection);

            JFrame reportViewerFrame = new JFrame("Visor de Informes");
            reportViewerFrame.setSize(800, 600);

            JasperViewer jasperViewer = new JasperViewer(jasperPrint);
            reportViewerFrame.getContentPane().add(jasperViewer.getContentPane());

            reportViewerFrame.addWindowListener(new WindowAdapter() {
               
                @Override
                public void windowClosing(WindowEvent e) {
                    try {
                        connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                    reportViewerFrame.dispose();
                }
            });

            reportViewerFrame.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
        //====================================================================================================================

   public String FachadaObtenerAlumnoCarrera(String carrera) {
    Connection conexion = getConexion();
    String informacionAlumnos = "";

    try {
        // Consulta para la función almacenada
        String sql = "SELECT * FROM obtener_alumnos_por_carrera(?)";
        PreparedStatement pstm = conexion.prepareStatement(sql);
        pstm.setString(1, carrera);

        // Ejecutar la consulta
        ResultSet rs = pstm.executeQuery();
        informacionAlumnos += "NOMBRE   |   APELLIDO   |   SEMESTRE   |   PROMEDIO\n";
        while (rs.next()) {
            String nombre = rs.getString("nombre");
            String apellido = rs.getString("apellido");
            int semestre = rs.getInt("semestre");
            double promedio = rs.getDouble("promedio");

            informacionAlumnos += "- " + nombre + "   " + apellido + "         " + semestre + "          " + promedio + "\n";
        }
    } catch (SQLException e) {
        System.out.println("Error al llamar a la función obtener_alumnos_por_carrera");
        e.printStackTrace();
    }
    return informacionAlumnos;
}


    
    public static void main(String[] args) {
    Connection conexion = Conexion.getConexion();
    if (conexion != null) {
        System.out.println("¡Conexión exitosa!");
    } else {
        System.out.println("No se pudo conectar a la base de datos.");
    }
}

        //====================================================================================================================

   public static void FuncionImprimirUsuario(String usuario) {
    Connection c = Conexion.getConexion();
    String mensajeUsuario = "";

    try (PreparedStatement ps = c.prepareStatement("SELECT imprimirusuario(?)")) {
        ps.setString(1, usuario);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            mensajeUsuario = rs.getString(1); // Captura el resultado de la función
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    JOptionPane.showMessageDialog(null, mensajeUsuario);
}



}


