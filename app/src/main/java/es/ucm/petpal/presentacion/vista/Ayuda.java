package es.ucm.petpal.presentacion.vista;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import es.ucm.petpal.R;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

/**
 * Created by msalitu on 08/03/2016.
 */
public class Ayuda extends Activity{
    ImageViewTouch mImage;
    private String pantalla;

    protected void onCreate(Bundle savedInstanceState) {
        cargarTema();
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_ayuda);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            pantalla = bundle.getString("pantalla");
            mImage = (ImageViewTouch) findViewById(R.id.imageView);

            mImage.setSingleTapListener(
                    new ImageViewTouch.OnImageViewTouchSingleTapListener() {

                        @Override
                        public void onSingleTapConfirmed() {
                            Log.d("LOG_TAG", "onSingleTapConfirmed");
                        }
                    }
            );

            mImage.setDoubleTapListener(
                    new ImageViewTouch.OnImageViewTouchDoubleTapListener() {

                        @Override
                        public void onDoubleTap() {
                            Log.d("LOG_TAG", "onDoubleTap");
                        }
                    }
            );

            mImage.setOnDrawableChangedListener(
                    new ImageViewTouchBase.OnDrawableChangeListener() {

                        @Override
                        public void onDrawableChanged(Drawable drawable) {
                            Log.i("LOG_TAG", "onBitmapChanged: " + drawable);
                        }
                    }
            );

            switch (pantalla) {
                case "principal":
                    mImage.setImageResource(R.drawable.ayuda_main);
                    break;
                case "configuracion":
                    mImage.setImageResource(R.drawable.ayuda_main);
                    break;
                case "nuevoPost":
                    mImage.setImageResource(R.drawable.ayuda_main);
                    break;
                case "verPosts":
                    mImage.setImageResource(R.drawable.ayuda_main);
                    break;
            }
        }
    }

    public void volver(View v){
        finish();
    }

    public void cargarTema(){
        switch (Configuracion.temaActual){
            case "AS_theme_azul":
                setTheme(R.style.AS_tema_azul);
                break;
            case "AS_theme_rojo":
                setTheme(R.style.AS_tema_rojo);
                break;
            case "AS_theme_rosa":
                setTheme(R.style.AS_tema_rosa);
                break;
            case "AS_theme_verde":
                setTheme(R.style.AS_tema_verde);
                break;
            case "AS_theme_negro":
                setTheme(R.style.AS_tema_negro);
                break;
        }
    }
}
