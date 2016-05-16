package es.ucm.petpal.negocio.post;

import java.util.Date;

import es.ucm.petpal.negocio.usuario.TransferUsuario;


/**
 * Created by Jeffer on 04/03/2016.
 */
public class TransferPost {

    private Integer id;
    private String titulo;
    private String ubicacion;
    private String descripcion;
    private String imagen;
    private Date fecha;
    private TransferUsuario usuario;

    public TransferPost(){    }

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

    public TransferUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(TransferUsuario usuario) {
        this.usuario = usuario;
    }
}

