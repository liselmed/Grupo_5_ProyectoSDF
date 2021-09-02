package com.example.sdf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.EditText;
import android.widget.ImageView;
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

public class Seleccionar_fecha extends AppCompatActivity {
    EditText etPlannedDate;
    Button btn_monitoreo;
    RequestQueue requestQueue;
    ImageView back;
    String id_vaca;
    String codigo_vaca;
    TextView cod_vaca;
    String idusuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_fecha);
        etPlannedDate = (EditText)findViewById(R.id.etPlannedDate);
        btn_monitoreo= (Button)findViewById(R.id.btn_monitoreo);
        cod_vaca= (TextView)findViewById(R.id.cod_vaca);
        back= (ImageView)findViewById(R.id.back);

        Bundle obtenerDatos = getIntent().getExtras();
        id_vaca = obtenerDatos.getString("id_vaca");
        codigo_vaca= obtenerDatos.getString("codigo_vaca");
        idusuario= obtenerDatos.getString("id_usuario");
        cod_vaca.setText("Vaca: "+codigo_vaca);

        etPlannedDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.etPlannedDate:
                        showDatePickerDialog();
                        break;
                }
            }
        });

        btn_monitoreo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                obtener_idfecha("https://se-smartfarming.000webhostapp.com/send_date.php?fecha="+etPlannedDate.getText().toString()+"");
            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Menu_Usuario.class);
                intent.putExtra("id_usuario",idusuario);
                startActivity(intent);
                finish();
            }
        });
    }

    private void agregardatos(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                String[] temperatura1 = new String[response.length()];
                String[] ph1= new String[response.length()];
                String[] tiempo1 = new String[response.length()];
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        temperatura1[i] = jsonObject.get("temperatura").toString();
                        Log.d("temp",temperatura1[i]);
                        ph1[i] = jsonObject.get("ph").toString();
                        Log.d("ph",ph1[i]);
                        tiempo1[i] = jsonObject.get("tiempo_envio").toString();
                        Log.d("time",tiempo1[i]);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                Intent intent = new Intent(getApplicationContext(),Monitoreo_vaca.class);
                intent.putExtra("id_vaca",id_vaca);
                intent.putExtra("temperatura",temperatura1);
                intent.putExtra("ph",ph1);
                intent.putExtra("tiempo",tiempo1);
                intent.putExtra("id_usuario",idusuario);
                startActivity(intent);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No existen datos en esta fecha.", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void obtener_idfecha(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject =null;
                String fecha="";
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        fecha=jsonObject.get("id_fecha").toString();
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                if (fecha.isEmpty() || fecha.equals("")){
                    Toast.makeText(getApplicationContext(), "No existen datos en esta fecha", Toast.LENGTH_SHORT).show();
                }else{
                    agregardatos("https://se-smartfarming.000webhostapp.com/data_vaca.php?id_vaca="+id_vaca+"&id_fecha="+fecha+"");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No existen datos en esta fecha", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker,int year, int month, int day) {
                // +1 because January is zero
                 String month_correct="";
                if (month<10){
                    month_correct= "0"+(month+1);
                }else{
                    month_correct= month+1+"";
                }
                final String selectedDate = year + "-" + month_correct + "-" + day;
                etPlannedDate.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

}