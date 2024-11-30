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
            pstm = conexion.prepareStatement("INSERT INTO paciente VALUES (?,?,?,?,?,?,?,?)");

            pstm.setInt(1, alum.getNumeroControl());
            pstm.setString(2, alum.getNombre());
            pstm.setString(3, alum.getPrimerAp());
            pstm.setString(4, alum.getSegundorAp());
            pstm.setString(5, alum.getCarrera());
            pstm.setInt(6, alum.getSemestre());
            pstm.setInt(7, alum.getEdad());
            pstm.setDouble(8, alum.getPromedio());
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

    public static boolean FachadaAgregarAlumnos(alumno alum){
        Connection conexion = getConexion();
        try {
            conexion.setAutoCommit(false);
            pstm = conexion.prepareStatement("INSERT INTO alumno VALUES (?,?,?,?,?,?,?,?)");

            pstm.setInt(1, alum.getNumeroControl());
            pstm.setString(2, alum.getNombre());
            pstm.setString(3, alum.getPrimerAp());
            pstm.setString(4, alum.getSegundorAp());
            pstm.setString(5, alum.getCarrera());
            pstm.setInt(6, alum.getSemestre());
            pstm.setInt(7, alum.getEdad());
            pstm.setDouble(8, alum.getPromedio());
            pstm.executeUpdate();
            
            conexion.commit();
            return true;        
        } catch (SQLException e) {
            System.out.println("Error en AgregarAlumno en archivo Fachada.java");
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
    }
        //====================================================================================================================

  
    public static boolean FachadaEliminarAlumno(String filtro){
        boolean res = false;
        Connection conexion = getConexion();
        try {
            conexion.setAutoCommit(false);
            String sql= "DELETE from paciente where NumeroControl= '"+filtro+"'";
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
            pstm = conexion.prepareStatement("UPDATE alumno SET Estado_Cuenta_Paciente=?,Nombre=?,Apellido=?,Direccion=?,Num_Telefono=?,Seguro_Medico=?,Ubicacion=? WHERE IdPaciente=" + alum.getNumeroControl());
            pstm.setString(1, alum.getNombre());
            pstm.setString(2, alum.getPrimerAp());
            pstm.setString(3, alum.getSegundorAp());
            pstm.setString(4, alum.getCarrera());
            pstm.setInt(5, alum.getSemestre());
            pstm.setInt(6, alum.getEdad());
            pstm.setDouble(7, alum.getPromedio());
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
           String reportPath = "C:\\Users\\Alfredo\\JaspersoftWorkspace\\MyReports\\REPORTE_PACIENTE.jasper";

            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportPath);

            String url = "jdbc:postgresql://localhost:5432/proyecto_clinica";
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

    public String FachadaObtenerAlumnoCarrera(String Ciudad) {
        Connection conexion = getConexion();
        String informacionPaciente = "";

        try {
            pstm = conexion.prepareStatement("SELECT  * FROM obtener_pacientes_por_ciudad(?) AS Pacientes_Region");
            pstm.setString(1, Ciudad);
            ResultSet rs = pstm.executeQuery();
            informacionPaciente +=" NOMBRE  "+"  APELLIDO  " +" SALA  " +"                    CIUDAD" + "\n";
            while (rs.next()) {
            
            String nombre = rs.getString("nombre");  // Obtiene nombre de la columna
            String apellido = rs.getString("apellido");  // Obtiene apellido de la columna
            String ubicacion = rs.getString("ubicacion");  // Obtiene ubicacion de la columna
            String direccion = rs.getString("direccion");  // Obtiene direccion de la columna

            informacionPaciente += "- "+nombre + "   " + apellido + "         " + ubicacion + "    " + direccion + "\n";
        }
        } catch (SQLException e) {
            System.out.println("Error al llamar a la función obtener_pacientes_por_ciudad");
            e.printStackTrace();
        } 
        return informacionPaciente;
    }
    
    
        //====================================================================================================================

    public static void FuncionImprimirUsuario(String usuario) {
        Connection c = Conexion.getConexion();
        String MensajeUsuario ="";
            String call = "{ call ImprimirUsuario(?) }";
            
            try (CallableStatement cs = c.prepareCall(call)) {
                cs.setString(1, usuario);
                
                cs.execute();
                
               SQLWarning warning = cs.getWarnings();
                while (warning != null) {
                    MensajeUsuario += warning.getMessage() + "\n";
                    warning = warning.getNextWarning();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
            JOptionPane.showMessageDialog(null, MensajeUsuario);
    }
}

