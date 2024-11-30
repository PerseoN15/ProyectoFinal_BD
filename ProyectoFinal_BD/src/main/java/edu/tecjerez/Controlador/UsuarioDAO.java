/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Conexion.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import vista.Login;
import MODELO.*;

/**
 *
 * @author anemn
 */
public class UsuarioDAO implements Runnable{
	Conexion conexion;
	private String filtro;

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}
	
	public ArrayList<Usuario> buscarUsuario (String filtro){
		ArrayList<Usuario> listaUsuarios = new ArrayList<>();

		ResultSet rs= conexion.BuscarAlumno(filtro);

		try {
			if(rs.next()) {
				do {
		listaUsuarios.add(new Usuario(
                   rs.getString(1),
                rs.getString(2)));
					Login.bandera = true;
				}while(rs.next());
			}else {
				Login.bandera = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Login.bandera = false;
		}

		return listaUsuarios;
	}
	@Override
	public void run() {
		buscarUsuario(this.filtro);

	}

    

}
