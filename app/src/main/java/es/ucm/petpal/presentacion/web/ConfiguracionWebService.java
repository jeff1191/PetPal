package es.ucm.petpal.presentacion.web;

/**
 * Created by Jeffer on 14/05/2016.
 */
public class ConfiguracionWebService {
    //IP DEL SERVIDOR, TIENE QUE SER LOCALHOST Y NO PUEDE SER EL DE LA COMPLUTENSE QUE LO TIENE CAPADO ESTE TIPO DE CONEXIONES
    private static final String IP = "192.168.1.107";
    public static final String WEB_VIEW = "http://" + IP  + ":80/petpal/app/index.html";

    public static final String GET_POSTS = "http://" + IP  + "/petpal/bdd/mostrar_posts.php";
    public static final String INSERT_POST = "http://" + IP  + "/petpal/bdd/insertar_post.php";

}