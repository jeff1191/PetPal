package es.ucm.petpal.presentacion.web;

/**
 * Created by Jeffer on 14/05/2016.
 */
public class ConfiguracionWebService {
    /**
     * Transición Home -> Detalle
     */
    public static final int CODIGO_DETALLE = 100;

    /**
     * Transición Detalle -> Actualización
     */
    public static final int CODIGO_ACTUALIZACION = 101;

    //IP DEL SERVIDOR, TIENE QUE SER LOCALHOST Y NO PUEDE SER EL DE LA COMPLUTENSE QUE LO TIENE CAPADO ESTE TIPO DE CONEXIONES
    private static final String IP = "192.168.1.103";


    public static final String GET = "http://" + IP  + "/IWish/obtener_metas.php";
    public static final String GET_BY_ID = "http://" + IP  + "/IWish/obtener_meta_por_id.php";
    public static final String UPDATE = "http://" + IP + "/IWish/actualizar_meta.php";
    public static final String DELETE = "http://" + IP +"/IWish/borrar_meta.php";
    public static final String INSERT = "http://" + IP +"/IWish/insertar_meta.php";




    public static final String GET_POSTS = "http://" + IP  + "/petpal/mostrar_posts.php";
    public static final String INSERT_POST = "http://" + IP  + "/petpal/insertar_post.php";
    /**
     * Clave para el valor extra que representa al identificador de una meta
     */
    public static final String EXTRA_ID = "IDEXTRA";
}
