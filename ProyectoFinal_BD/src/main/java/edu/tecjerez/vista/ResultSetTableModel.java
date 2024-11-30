/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author anemn
 */
public class ResultSetTableModel extends AbstractTableModel {
    private Connection conexion;
    private Statement instruccion;
    private ResultSet conjuntoResultados;
    private ResultSetMetaData metaDatos;
    private int numeroDeFilas;
    private boolean conectadoALaBaseDeDatos = false;

    public ResultSetTableModel(String controlador, String url, String consulta) throws SQLException, ClassNotFoundException {
        Class.forName(controlador);
        conexion = DriverManager.getConnection(url, "postgres", "itsj");
        instruccion = conexion.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        conectadoALaBaseDeDatos = true;
        establecerConsulta(consulta);
    }

    public Class getColumnClass(int columna) throws IllegalStateException {
        if (!conectadoALaBaseDeDatos)
            throw new IllegalStateException("No hay conexion a la base de datos");
        try {
            String nombreClase = metaDatos.getColumnClassName(columna + 1);
            return Class.forName(nombreClase);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
        }
        return Object.class;
    }

    public int getColumnCount() throws IllegalStateException {
        if (!conectadoALaBaseDeDatos)
            throw new IllegalStateException("No hay conexion a la base de datos");
        try {
            return metaDatos.getColumnCount();
        } catch (SQLException excepcionSQL) {
            excepcionSQL.printStackTrace();
        }
        return 0;
    }

    public String getColumnName(int columna) throws IllegalStateException {
        if (!conectadoALaBaseDeDatos)
            throw new IllegalStateException("No hay conexion a la base de datos");
        try {
            return metaDatos.getColumnName(columna + 1);
        } catch (SQLException excepcionSQL) {
            excepcionSQL.printStackTrace();
        }
        return "";
    }

    public int getRowCount() throws IllegalStateException {
        if (!conectadoALaBaseDeDatos)
            throw new IllegalStateException("No hay conexion a la base de datos");
        return numeroDeFilas;
    }

    public Object getValueAt(int fila, int columna) throws IllegalStateException {
        if (!conectadoALaBaseDeDatos)
            throw new IllegalStateException("No hay conexion a la base de datos");
        try {
            conjuntoResultados.absolute(fila + 1);
            return conjuntoResultados.getObject(columna + 1);
        } catch (SQLException excepcionSQL) {
            excepcionSQL.printStackTrace();
        }
        return "";
    }

    public void establecerConsulta(String consulta) throws SQLException, IllegalStateException {
        if (!conectadoALaBaseDeDatos)
            throw new IllegalStateException("No hay conexion a la base de datos");
        conjuntoResultados = instruccion.executeQuery(consulta);
        metaDatos = conjuntoResultados.getMetaData();
        conjuntoResultados.last();
        numeroDeFilas = conjuntoResultados.getRow();
        fireTableStructureChanged();
    }

    public void desconectarDeLaBaseDeDatos() {
        try {
            instruccion.close();
            conexion.close();
        } catch (SQLException excepcionSQL) {
            excepcionSQL.printStackTrace();
        } finally {
            conectadoALaBaseDeDatos = false;
        }
    }
}
