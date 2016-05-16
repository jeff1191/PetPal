/**
 * 
 */
package es.ucm.petpal.integracion;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;


public class Post {
	@DatabaseField(generatedId = true, columnName = "ID")
	private Integer id;

	@DatabaseField(columnName = "TITULO")
	private String titulo;

	@DatabaseField(columnName = "UBICACION")
	private String ubicacion;

	@DatabaseField(columnName = "DESCRIPCION")
	private String descripcion;

	@DatabaseField(columnName = "IMAGEN")
	private String imagen;

	@DatabaseField(columnName = "FECHA" ,dataType = DataType.DATE_STRING, format = "dd-MM-yyyy HH:mm:ss")
	private Date fecha;

	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "USUARIO")
	private Usuario usuario;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}