package com.example.sdf;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adaptador extends BaseAdapter {

    private static LayoutInflater inflater = null;

    Context contexto;
    String[] codigos;
    int[] datosimg;
    int[] cantidad;

    public Adaptador (Context contexto, String[] codigos,int[] cantidad){
        this.contexto = contexto;
        this.codigos = codigos;
        this.cantidad = cantidad;
        inflater = (LayoutInflater)contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View vista = inflater.inflate(R.layout.activity_adaptador, null);
        TextView id = (TextView)vista.findViewById(R.id.id);
        TextView codigo = (TextView)vista.findViewById(R.id.codigo);
        final ImageView imagen = (ImageView) vista.findViewById(R.id.imageView);

        id.setText(id.getText().toString()+ cantidad[i]);
        codigo.setText(codigo.getText().toString() +" "+ codigos[i]);
        imagen.setImageResource(R.drawable.item);
        /*final String imageUrl = datos[i][5];
        Picasso.get()
                .load(imageUrl)
                .into(imagen, new Callback() {
                    @Override
                    public void onSuccess() {
                        Picasso.get().load(imageUrl).into(imagen);
                    }

                    @Override
                    public void onError(Exception e) {
                        imagen.setImageResource(R.drawable.portada4);
                    }
                });*/
        return vista;
    }

    @Override
    public int getCount() {
        return cantidad.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


}