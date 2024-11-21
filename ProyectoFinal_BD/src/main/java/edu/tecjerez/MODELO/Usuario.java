/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

/**
 *
 * @author anemn
 */
public class Usuario {
    private String Usuario;
	private String Contraseña;

	public Usuario(String usuario, String contraseña) {
		Usuario = usuario;
		Contraseña = contraseña;
	}

	public String getContraseña() {
		return Contraseña;
	}
	public void setContraseña(String contraseña) {
		this.Contraseña = contraseña;
	}

	public String getUsuario() {
		return Usuario;
	}

	public void setUsuario(String usuario) {
		Usuario = usuario;
	}
}
