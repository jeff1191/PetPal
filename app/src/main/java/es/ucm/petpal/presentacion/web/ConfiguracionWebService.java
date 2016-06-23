package es.ucm.petpal.presentacion.web;

/**
 * Created by Jeffer on 14/05/2016.
 */
public class ConfiguracionWebService {
    //IP DEL SERVIDOR, TIENE QUE SER LOCALHOST Y NO PUEDE SER EL DE LA COMPLUTENSE QUE LO TIENE CAPADO ESTE TIPO DE CONEXIONES
    private static final String IP = "192.168.1.102";
    public static final String WEB_VIEW = "http://" + IP  + ":80/petpal/app/index.php";
    public static final String INSERT_POST = "http://" + IP  + "/petpal/bdd/insertar_post.php";
    public static final String INSERT_USER = "http://" + IP  + "/petpal/bdd/insertar_usuario.php";
    public static final String GET_USER = "http://" + IP  + "/petpal/bdd/mostrar_usuario_id.php";
    public static final String GET_LOGIN = "http://" + IP  + "/petpal/bdd/login.php";
    public static final String GET_USER_EMAIL = "http://" + IP  + "/petpal/bdd/mostrar_usuario_correo.php";
    public static final String UPLOAD_IMAGE_USER = "http://" + IP  + "/petpal/bdd/upload_image_usuario.php";
    public static final String UPLOAD_IMAGE_POST = "http://" + IP  + "/petpal/bdd/upload_image_post.php";
}