package com.example.aaguilar.tonosnavideos;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvDatos;
    private Activity activity;
    private CustomAdapter adaptador;
    private ArrayList<Tema> datos;

    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (shouldAskPermissions()) {
            askPermissions();
        }

        setContentView(R.layout.activity_main);
        lvDatos = (ListView) findViewById(R.id.lvLista);
        activity = this;
        datos = new ArrayList<Tema>();

        llenarArrayList();
        adaptador = new CustomAdapter(activity, datos);
        lvDatos.setAdapter(adaptador);
        adaptador.getItem(5);
    }

    public void llenarArrayList() {
        Resources resources = getResources();
        String[] arrayNombres = resources.getStringArray(R.array.titulo);
        String[] arrayTipos = resources.getStringArray(R.array.duracion);
        TypedArray imgs = getResources().obtainTypedArray(R.array.icono);
        TypedArray cancion = getResources().obtainTypedArray(R.array.cancion);
        String[] arrayInsertar = resources.getStringArray(R.array.insertar);

        for (int i = 0; i < arrayNombres.length; i++) {
            datos.add(new Tema(arrayNombres[i], arrayTipos[i], imgs.getResourceId(i, -1),
                    cancion.getResourceId(i, -1), arrayInsertar[i] ));
        }
    }
}
