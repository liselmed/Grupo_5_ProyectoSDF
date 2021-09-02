package com.example.sdf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.Inet4Address;
import java.util.ArrayList;

public class Menu_Usuario extends AppCompatActivity {
    String idusuario;
    RequestQueue requestQueue;
    ListView lista_vaca;
    ImageView logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_usuario);
        lista_vaca= (ListView)findViewById(R.id.lista_vaca);
        logOut= (ImageView) findViewById(R.id.logOut);
        Bundle obtenerDatos = getIntent().getExtras();
        idusuario = obtenerDatos.getString("id_usuario");
        agregarVacas("https://se-smartfarming.000webhostapp.com/send_cow.php?id_usuario="+idusuario+"");

        logOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(),Inicio_sesion.class);
                startActivity(intent2);
                finish();
            }
        });

    }
    private void agregarVacas(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject =null;
                String[] vacas = new String[response.length()];
                int[] n_vacas= new int[response.length()];
                String[] id_vaca= new String[response.length()];
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        id_vaca[i]=jsonObject.get("id_vaca").toString();
                        vacas[i]=jsonObject.get("codigo_vaca").toString();;
                        n_vacas[i]= i+1;
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                agregar_lista(vacas,n_vacas,id_vaca);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR DE CONEXIÓN", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void agregar_lista(String[] v,int[] cant, String[] id_v){
        lista_vaca = (ListView) findViewById(R.id.lista_vaca);
        lista_vaca.setAdapter(new Adaptador(this, v,cant));
        lista_vaca.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long id) {
                Intent intent = new Intent(view.getContext(),Seleccionar_fecha.class);
                intent.putExtra("id_vaca",id_v[posicion]);
                intent.putExtra("codigo_vaca",v[posicion]);
                intent.putExtra("id_usuario",idusuario);
                startActivity(intent);
                finish();
            }
        });
    }
}