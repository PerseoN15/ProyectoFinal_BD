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
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

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

    public static boolean FachadaActualizarAlumnos(alumno alum) {
    Connection conexion = getConexion();

    try {
        // Validar que el promedio cumpla con las restricciones
        if (alum.getPromedio() < 0 || alum.getPromedio() > 100) {
            System.out.println("Error: El promedio debe estar entre 0 y 100.");
            return false;
        }

        conexion.setAutoCommit(false); // Inicia la transacción

        // Preparar la consulta de actualización
        pstm = conexion.prepareStatement("UPDATE alumno SET Nombre = ?, PrimerAp = ?, SegundoAp = ?, Carrera = ?, Semestre = ?, Edad = ?, Promedio = ? WHERE NumeroControl = ?");
        pstm.setString(1, alum.getNombre());
        pstm.setString(2, alum.getPrimerAp());
        pstm.setString(3, alum.getSegundorAp());
        pstm.setString(4, alum.getCarrera());
        pstm.setInt(5, alum.getSemestre());
        pstm.setInt(6, alum.getEdad());
        pstm.setDouble(7, alum.getPromedio());
        pstm.setInt(8, alum.getNumeroControl());

        pstm.executeUpdate(); // Ejecutar la consulta

        conexion.commit(); // Confirmar la transacción
        return true;

    } catch (SQLException e) {
        System.out.println("Error en ActualizarPaciente en Fachada.java");
        e.printStackTrace();
        try {
            conexion.rollback(); // Revertir la transacción en caso de error
        } catch (SQLException ex) {
            System.out.println("Error en el rollback de actualizaciones");
            ex.printStackTrace();
        }
    } finally {
        try {
            conexion.setAutoCommit(true); // Restaurar el comportamiento por defecto
            if (pstm != null) {
                pstm.close();
            }
        } catch (SQLException m) {
            m.printStackTrace();
        }
    }
    return false;
}

    
      //====================================================================================================================
  
   public static void generarInforme(JFrame parentFrame) {
    try {
        // Ruta del archivo del reporte Jasper
        String reportPath = "C:\\Users\\anemn\\OneDrive\\Documentos\\NetBeansProjects\\ProyectoTBD\\src\\main\\java\\JASPERREPORT\\null.jasper"; 

        // Cargar el reporte
        JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportPath);

        // Datos de conexión a la base de datos
        String url = "jdbc:postgresql://localhost:5432/proyecto_tutorias";
        String user = "postgres"; 
        String password = "itsj"; 
        Connection connection = DriverManager.getConnection(url, user, password);

        
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("titulo", "Reporte de Alumnos"); 

        // Llenar el reporte con los datos
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, connection);

        // Crear el visor de informes
        JFrame reportViewerFrame = new JFrame("Visor de Informes");
        reportViewerFrame.setSize(800, 600);

        JasperViewer jasperViewer = new JasperViewer(jasperPrint);
        reportViewerFrame.getContentPane().add(jasperViewer.getContentPane());

        // Manejo del cierre del visor
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
        JOptionPane.showMessageDialog(parentFrame, 
            "Error al generar el informe: " + e.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
    }
}
        //====================================================================================================================

public String FachadaObtenerAlumnosPorCarrera(String carrera) {
    StringBuilder informacionAlumnos = new StringBuilder();
    String sql = "SELECT * FROM obtener_alumnos_por_carrera(?)";

    // Obtener la conexión
    try (Connection conexion = Conexion.getConexion()) {

        // Verificar si la conexión es válida
        if (conexion == null || conexion.isClosed()) {
            System.err.println("Error: Conexión no establecida.");
            return "Error: Conexión no establecida.";
        }

        // Preparar la consulta
        try (PreparedStatement pstm = conexion.prepareStatement(sql)) {
            pstm.setString(1, carrera);

            // Ejecutar la consulta y procesar resultados
            try (ResultSet rs = pstm.executeQuery()) {
                informacionAlumnos.append("NUM. CONTROL \tNOMBRE \t\tAPELLIDOS \t\tCARRERA \t\tSEMESTRE \n");
                informacionAlumnos.append("-----------------------------------------------------------------------------\n");

                while (rs.next()) {
                    informacionAlumnos.append(rs.getInt("numerocontrol"))
                                      .append("\t ")
                                      .append(rs.getString("nombre"))
                                      .append("\t\t ")
                                      .append(rs.getString("primerap"))
                                      .append(" ")
                                      .append(rs.getString("segundoap"))
                                      .append("\t\t ")
                                      .append(rs.getString("carrera"))
                                      .append("\t\t ")
                                      .append(rs.getInt("semestre"))
                                      .append("\n");
                }
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener alumnos por carrera: " + e.getMessage());
        informacionAlumnos.append("Error al obtener alumnos por carrera.");
    }

    return informacionAlumnos.toString();
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
/*public String FachadaObtenerAlumnosPorCarrera(String carrera) {
    Connection conexion = getConexion(); 
    String informacionAlumnos = "";

    try {
        
        pstm = conexion.prepareStatement("SELECT * FROM obtener_alumnos_por_carrera(?) AS Alumnos_Carrera");
        pstm.setString(1, carrera); // Establecer el parámetro de la carrera
        ResultSet rs = pstm.executeQuery();

        // Encabezado de los datos
        informacionAlumnos += "NUM. CONTROL\tNOMBRE\t\tAPELLIDOS\t\tCARRERA\t\tSEMESTRE\n";
        informacionAlumnos += "-----------------------------------------------------------------------------\n";

        // Recorrer el resultado de la consulta
        while (rs.next()) {
            String numeroControl = rs.getString("numero_control");  
            String nombre = rs.getString("nombre");                
            String apellidos = rs.getString("apellidos");          
            String carreraDB = rs.getString("carrera");           
            int semestre = rs.getInt("semestre");              

            informacionAlumnos += numeroControl + "\t" 
                                + nombre + "\t\t" 
                                + apellidos + "\t\t" 
                                + carreraDB + "\t\t" 
                                + semestre + "\n";
        }
    } catch (SQLException e) {
        System.out.println("Error al llamar a la función obtener_alumnos_por_carrera");
        e.printStackTrace();
    } finally {
        try {
            if (conexion != null) {
                conexion.close(); // Cerrar la conexión
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return informacionAlumnos; // Devolver la información recopilada
}


*/
}


