package com.example.aaguilar.tonosnavideos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by Antonio Aguilar on 12/21/16.
 */

public class CustomAdapter extends BaseAdapter {

    private ArrayList<Tema> items;
    Context context;
    private MediaPlayer mp = new MediaPlayer();
    int songs[] = {R.raw.r1, R.raw.r2, R.raw.r3, R.raw.r4, R.raw.r5, R.raw.r6, R.raw.r7, R.raw.r8,
            R.raw.r9, R.raw.r10, R.raw.r11, R.raw.r12, R.raw.r13, R.raw.r14, R.raw.r15, R.raw.r16,
            R.raw.r18, R.raw.r19, R.raw.r20};


    public CustomAdapter(Activity activity, ArrayList data) {
        this.context = activity;
        this.items = data;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lista, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Typeface tf_thing = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_thin.ttf");
        Typeface tf_bold = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_light.ttf");
        viewHolder.itemNombre.setTypeface(tf_bold);
        viewHolder.itemTipo.setTypeface(tf_thing);
        final Tema currentItem = (Tema) getItem(position);
        final int pos = position;
        viewHolder.itemNombre.setText(currentItem.getTitulo());
        viewHolder.itemTipo.setText(currentItem.getDuracion());
        viewHolder.itemImagen.setImageResource(currentItem.getIcono());

        //Metodo que reproduce una cancion
        viewHolder.itemImagePlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mp.stop();
                mp = MediaPlayer.create(context, songs[pos]);
                mp.start();
            }
        });

        //Metodo que detiene una cancion
        viewHolder.itemImageStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mp.stop();
            }
        });

        //Envia una ruta, el tipo de accion y la posicion de la vista,
        // para dada una eleccion entre ringtone, notificacion o alarma
        viewHolder.itemInsercion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Settings.System.canWrite(context)) {
                        final CharSequence[] items = {"Establecer como Ringtone", "Establecer como SMS/Notificación", "Establecer como Alarma"};
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                switch (item){
                                    case 0:
                                        String pathRingtone = Environment.getExternalStorageDirectory().getPath()+ "/media/audio/ringtones/";
                                        guardarComo(RingtoneManager.TYPE_RINGTONE, pos, pathRingtone);
                                        Toast.makeText(context, "Se estableció como Ringtone", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 1:
                                        String pathSms = Environment.getExternalStorageDirectory().getPath()+ "/media/audio/notifications/";
                                        guardarComo(RingtoneManager.TYPE_NOTIFICATION, pos, pathSms);
                                        Toast.makeText(context, "Se estableció como SMS/Notificación", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 2:
                                        String pathAlarma = Environment.getExternalStorageDirectory().getPath()+ "/media/audio/alarms/";
                                        guardarComo(RingtoneManager.TYPE_ALARM, pos, pathAlarma);
                                        Toast.makeText(context, "Se estableció como Alarma", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                                dialog.cancel();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();}
                    else {
                        Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                        intent.setData(Uri.parse("package:" + context.getPackageName()));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            }
        });
        return convertView;
    }

    public boolean guardarComo(int type, int p, String path) {
        byte[] buffer = null;
        InputStream fIn = context.getResources().openRawResource(songs[p]);
        int size = 0;
        try {
            size = fIn.available();
            buffer = new byte[size];
            fIn.read(buffer);
            fIn.close();
        } catch (IOException e) {
            return false;
        }
        String filename = this.items.get(p).getInsercion();
        boolean exists = (new File(path)).exists();
        if (!exists) {
            new File(path).mkdirs();
        }
        FileOutputStream save;
        try {
            save = new FileOutputStream(path + filename);
            save.write(buffer);
            save.flush();
            save.close();
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path + filename)));

        File k = new File(path, filename);
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DATA, k.getAbsolutePath());
        values.put(MediaStore.MediaColumns.TITLE, filename);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");

        if (RingtoneManager.TYPE_RINGTONE == type) {
            values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
        } else if (RingtoneManager.TYPE_NOTIFICATION == type) {
            values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
        } else if (RingtoneManager.TYPE_ALARM == type) {
            values.put(MediaStore.Audio.Media.IS_ALARM, true);
        }

        Uri uri = MediaStore.Audio.Media.getContentUriForPath(k.getAbsolutePath());
        Uri newUri = this.context.getContentResolver().insert(uri, values);
        RingtoneManager.setActualDefaultRingtoneUri(this.context, type, newUri);
        context.getContentResolver().insert(MediaStore.Audio.Media.getContentUriForPath(k.getAbsolutePath()), values);
        return true;
    }

    private class ViewHolder {
        TextView itemNombre;
        TextView itemTipo;
        ImageView itemImagen;
        ImageView itemImagePlay;
        ImageView itemInsercion;
        ImageView itemImageStop;

        public ViewHolder(View view) {
            itemNombre = (TextView)view.findViewById(R.id.tvTitulo);
            itemTipo = (TextView) view.findViewById(R.id.tvSubtitulo);
            itemImagen = (ImageView) view.findViewById(R.id.ivImagen);
            itemImagePlay = (ImageView) view.findViewById(R.id.ivPlay);
            itemInsercion = (ImageView) view.findViewById(R.id.ivInsert);
            itemImageStop = (ImageView) view.findViewById(R.id.ivStop);
        }
    }
}