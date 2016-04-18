/**
 * 
 */
package es.ucm.petpal.negocio.usuario;

import java.util.List;


public class TransferUsuario {

	private Integer id;

	private String nombre;

	private String correo;

	private String avatar;

	private String informe;

	private Integer puntuacion;

	private String color;

	private String tono;

	private String nombreTutor;

	private String correoTutor;

	private Integer puntuacionAnterior;

	public TransferUsuario(){	}

	public TransferUsuario(Usuario usuario){
		this.id = usuario.getId();
		this.nombre = usuario.getNombre();
		this.correo = usuario.getCorreo();
		this.avatar = usuario.getAvatar();
		this.puntuacion = usuario.getPuntuacion();
		this.color = usuario.getColor();
		this.tono = usuario.getTono();
		this.nombreTutor = usuario.getNombreTutor();
		this.correoTutor = usuario.getCorreoTutor();
		this.puntuacionAnterior = usuario.getPuntuacionAnterior();
	}

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

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getInforme() {
		return informe;
	}

	public void setInforme(String informe) {
		this.informe = informe;
	}

	public Integer getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(Integer puntuacion) {
		this.puntuacion = puntuacion;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTono() {
		return tono;
	}

	public void setTono(String tono) {
		this.tono = tono;
	}

	public String getNombreTutor() {
		return nombreTutor;
	}

	public void setNombreTutor(String nombreTutor) {
		this.nombreTutor = nombreTutor;
	}

	public String getCorreoTutor() {
		return correoTutor;
	}

	public void setCorreoTutor(String correoTutor) {
		this.correoTutor = correoTutor;
	}

	public Integer getPuntuacionAnterior() {
		return puntuacionAnterior;
	}

	public void setPuntuacionAnterior(Integer puntuacionAnterior) {
		this.puntuacionAnterior = puntuacionAnterior;
	}
}