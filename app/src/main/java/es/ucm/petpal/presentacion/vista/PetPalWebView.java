package es.ucm.petpal.presentacion.vista;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import es.ucm.petpal.R;

/**
 * Created by Juan Lu on 11/05/2016.
 */
public class PetPalWebView extends Activity{

    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_web_view);

        myWebView = (WebView) this.findViewById(R.id.webView);
        myWebView.loadUrl("https://google.es");

    }

    public void volver(View v){
        Intent pantallaPrincipal = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(pantallaPrincipal);
    }
}
