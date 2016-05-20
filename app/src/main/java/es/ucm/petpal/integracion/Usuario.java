/**
 * 
 */
package es.ucm.petpal.integracion;

import com.j256.ormlite.field.DatabaseField;


public class Usuario {

	@DatabaseField(generatedId = true, columnName = "ID")
	private Integer id;

	@DatabaseField(columnName = "NOMBRE")
	private String nombre;

	@DatabaseField(columnName = "APELLIDOS")
	private String apellidos;

	@DatabaseField(columnName = "EMAIL")
	private String email;

	@DatabaseField(columnName = "AVATAR")
	private String avatar;

	@DatabaseField(columnName = "TELEFONO")
	private String telefono;

	@DatabaseField(columnName = "CIUDAD")
	private String ciudad;

	@DatabaseField(columnName = "COLOR")
	private String color;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}